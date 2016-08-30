package com.example.cmu.cmu_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cmu.com.example.cmu.Rest.NetworkService;
import com.example.cmu.mqtt.IReceivedMessageListener;
import com.example.cmu.mqtt.MQTTUtils;
import com.example.cmu.mqtt.ReceivedMessage;
import com.example.cmu.object.Result_Resv_Info;
import com.example.cmu.object.Send_Resv_Info;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 동재 on 2016-07-05.
 */
public class Reservation extends Activity {

    final public String RESERVATION = "Reservation activity";

    private AlertDialog mDialog = null;
    private EditText phoneNum = null;
    private EditText credit = null;
    private TimePicker resv_time = null;
    private Button submit = null;
    private final int TIME_PICKER_INTERVAL = 10;
    private Vibrator vibrator;

    Send_Resv_Info rio;
    NetworkService ns;
    AsyncReservation aResv;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int status = 0; //whether reservation is success or not
    int resvHour = 0; // current reservation hour
    int resvMin = 0; // current reservation minute
    String pNum = ""; // user phone_number
    String cNum = ""; // user card_number

    private MainActivity tabs = null;

    //connect to push server
    String pushServerIp = "192.168.1.169";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ns = new NetworkService();
        aResv = new AsyncReservation();
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        phoneNum = (EditText) findViewById(R.id.v_r_editText_phoneNum);
        credit = (EditText) findViewById(R.id.v_r_editText_credit);
        resv_time = (TimePicker) findViewById(R.id.v_r_timePicker);

        setCustomTimePicker(resv_time);

        MQTTUtils mqttUtils = MQTTUtils.getInstance();
        mqttUtils.addReceivedMessageListner(new IReceivedMessageListener() {
            @Override
            public void onMessageReceived(ReceivedMessage message) {
                String messageContext = message.getMessage().toString();
                Log.d("abc", "topic:" + message.getTopic() + " message: " + messageContext + " timestamp: " + message.getTimestamp());
                vibrator.vibrate(700);
            }
        });

        submit = (Button) findViewById(R.id.v_r_button_reserve);
        submit.setOnClickListener(new View.OnClickListener() {
            // do reservation button
            @Override
            public void onClick(View view) {

                pNum = phoneNum.getText().toString();
                cNum = credit.getText().toString();
                resvHour = resv_time.getCurrentHour();
                resvMin = resv_time.getCurrentMinute();

                rio = new Send_Resv_Info();

                rio.setResv_phonenum(pNum);
                rio.setResv_creditnum(cNum);
                rio.setResv_starttime(String.valueOf(resvHour) + ":" + String.valueOf(resvMin) + "0");

                //Limit to reservation within 3 hours
                long now = System.currentTimeMillis(); //current time
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
                String strNow = sdfNow.format(date);
                String[] curHourMinute = strNow.split(":");
                int currentTime = Integer.parseInt(curHourMinute[0]) * 60 + Integer.parseInt(curHourMinute[1]); // calculate from current time to minute
                int reservedTime = resvHour * 60 + resvMin; // calculate from reserved time to minute

                Log.d("abc", "timeCompare: " + (reservedTime - currentTime));

                //Condition to reservation timeZ
                if (reservedTime - currentTime > 180 || reservedTime - currentTime < 0) {
                    if (reservedTime - currentTime > 180) {
                        status = 1;
                    } else if (reservedTime - currentTime < 0) {
                        if (reservedTime - currentTime + 1440 > 180) {
                            status = 1;
                        } else {
                            status = 0;
                            MQTTUtils.connect(pushServerIp, getApplicationContext());

                            String send_data = ns.getGsonFromResv(rio);
                            Insert_reservation(send_data);
                        }
                    }
                } else if (pNum.length() < 9 || pNum.length() > 12)
                    status = 2;
                else if (cNum.length() != 16)
                    status = 3;
                else {
                    //when the condition of reservation is satisfied, the information are saved in sharedpreference
                    Log.d("abc", "reservation condition satisfied");
                    status = 0;

                    MQTTUtils.connect(pushServerIp, getApplicationContext());

                    // parsing reservation information to json
                    String send_data = ns.getGsonFromResv(rio);
                    //send json data to server
                    Insert_reservation(send_data);
                }
                if (status != 0) {
                    mDialog = createDialog(status);
                    mDialog.show();
                }
            }
        });
    }

    private AlertDialog createDialog(int status) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Notification");

        final int rFlag = status;

        if (status == 0) {
            ab.setMessage("Reservation succeeded");
        } else if (status == 1) {
            ab.setMessage("Reservation failed: Not allowed reservation time");
            resv_time.setCurrentHour(0);
            resv_time.setCurrentMinute(0);
        } else if (status == 2) {
            ab.setMessage("Reservation failed: Wrong phone number");
            phoneNum.setText("");
        } else if (status == 3) {
            ab.setMessage("Reservation failed: Wrong credit card number");
            credit.setText("");
        } else if (status == 4) {
            ab.setMessage("Reservation failed: No parking space left");
        }

        ab.setCancelable(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();

                    if (rFlag == 0) {
                        tabs = (MainActivity) getParent();
                        //If push the "OK" button, then move to tab 1 activity (reservation_check.class)
                        tabs.getTabHost().getTabWidget().getChildAt(1).setEnabled(true);
                        tabs.getTabHost().setCurrentTab(1);
                    }
                }
            }
        });

        return ab.create();
    }

    //예약하기
    public class AsyncReservation extends AsyncTask<String, Integer, String> {

        private ProgressDialog mDlg;

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(Reservation.this);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("Doing reservation..");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return ns.HttpSendData(params[0], ns.reserv_path);
        }

        protected void onPostExecute(String str) {

            //parsing Result value that is received from server to Reservation object
            Result_Resv_Info rri = ns.setDataFromGson(str);

            try {
                Log.i("abc", "AsyncReservation, OnPost" + str);

                if (!"null".equals(str)) {
                    //If the reservation is success, then authentication_number is saved in sharedpreference
                    editor = pref.edit();

                    if (resvMin < 10) // if: if the minute is under 10 minute, then add "0" to minute,  ex. 01 02 03 ...
                        editor.putString("reservedTime", String.valueOf(resvHour) + ":0" + String.valueOf(resvMin));
                    else
                        editor.putString("reservedTime", String.valueOf(resvHour) + ":" + String.valueOf(resvMin));
                    editor.putString("phoneNum", pNum);
                    editor.putInt("flag", 1);

                    editor.putString("park_facility", rri.getParkingfacility_id());
                    editor.putString("park_floor", rri.getParkingslot_floor());
                    editor.putString("park_zone", rri.getParkingslot_zone());
                    editor.putString("park_slot", rri.getParkingslot_id());
                    editor.putString("authen_num", rri.getResv_authenticationnum());
                    //if Reservation is success, then it can't move from reservation_check to reservation class
                    editor.putBoolean("Reservation", true);

                    editor.commit();
                    status = 0;

                    //register topic value on authentication_number for receiving push alarm
                    String topic = rri.getResv_authenticationnum();
                    MQTTUtils.sub(topic);

                } else {
                    //If there is no available seat
                    status = 4;
                }
                mDialog = createDialog(status);
                mDialog.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mDlg.dismiss();
        }
    }

    //The function to reserve the parking slot
    public void Insert_reservation(String data) {
        if (aResv == null) {
            aResv.execute(data);
        } else {
            aResv = new AsyncReservation();
            aResv.execute(data);
        }
    }

    //The timepicker view that is used to set the reservation time
    public void setCustomTimePicker(TimePicker timePicker) {
        timePicker.setIs24HourView(true);

        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field field = classForid.getField("minute");
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(field.getInt(null));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(5);
            ArrayList<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Reservation onResume()", Toast.LENGTH_SHORT).show();
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        //if the push msg is "payment", then texts  should be clear
        try {
            if(pref.getInt("flag",3)==0){
                phoneNum.setText("");
                credit.setText("");

                editor.putString("park_facility",null);
                editor.putString("park_floor", null);
                editor.putString("park_zone", null);
                editor.putString("park_slot", null);
                editor.putString("authen_num", null);
                editor.putBoolean("Reservation", false);

                editor.commit();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}