package com.example.cmu.cmu_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmu.com.example.cmu.Rest.NetworkService;
import com.example.cmu.object.Result_Resv_Info;

/**
 * Created by ajou on 2016-07-01.
 */
public class Reservation_check extends Activity {

    final public String CHECK = "Reservation_check";

    private AlertDialog mDialog = null;
    private TextView resvTime = null;
    private TextView pFacility = null;
    private TextView pFloor = null;
    private TextView pZone = null;
    private TextView pSlot = null;
    private TextView authen_Num = null;
    private MainActivity tabs = null;
    private Button cancel = null;
    private Button payment = null;

    NetworkService ns;
    SharedPreferences pref = null;
    SharedPreferences.Editor editor;

    String reservedTime = "";
    String parkingFacility = "";
    String parkingFloor = "";
    String parkingZone = "";
    String parkingSlot = "";
    String authenticationNum = "";
    String phone_num = "";
    Boolean ReservationFlag = false;

    AsyncReserCancel aCancel;
    AsyncReserCheck aCheck;

    //Status of reservation checking : 0 -> reservation_check is success, 1 -> fail
    int checkFlag = 0;
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser_check);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        ns = new NetworkService();
        aCancel = new AsyncReserCancel();
        aCheck = new AsyncReserCheck();

        resvTime = (TextView) findViewById(R.id.v_mL_textView_reservedTime);
        pFacility = (TextView) findViewById(R.id.v_mL_textView_setParkingFacility);
        pFloor = (TextView) findViewById(R.id.v_mL_textView_setParkingFloor);
        pZone = (TextView) findViewById(R.id.v_mL_textView_setParkingZone);
        pSlot = (TextView) findViewById(R.id.v_mL_textView_setParkingSlot);
        authen_Num = (TextView) findViewById(R.id.v_mL_textView_setAuthenNum);

        ReservationFlag = pref.getBoolean("Reservation", false);
        phone_num = pref.getString("phoneNum", "");
        authenticationNum = pref.getString("authen_num", "");

        //If the reserved time is shown in the TextView, then cannot move to reservation.class(activity)
        if (!resvTime.getText().toString().equals("")) {
            tabs = (MainActivity) getParent();
            tabs.getTabHost().getTabWidget().getChildAt(0).setEnabled(false);
        }

        cancel = (Button) findViewById(R.id.v_mL_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!authenticationNum.equals("null")) {
                    cancel_reservation(authenticationNum);
                } else {
                    //cancel is fail
                    status = 1;
                    mDialog = createDialog(status);
                    mDialog.show();
                }
            }
        });
    }
    //The dialog that is used to show the result
    private AlertDialog createDialog(int status) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Notification");

        final int gFlag = status;

        if (status == 0) {
            ab.setMessage("Reservation cancel succeeded");
        } else if (status == 1) {
            ab.setMessage("Reservation cancel failed ");
        } else if (status == 2) {
            ab.setMessage("No Reservation");
        }

        ab.setCancelable(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (mDialog != null && mDialog.isShowing()) {
                    if (gFlag == 0) {

                        //If the cancel button is clicked, then all of view is initialized,
                        editor.clear();
                        editor.commit();
                        resvTime.setText("");
                        pFacility.setText("");
                        pFloor.setText("");
                        pZone.setText("");
                        pSlot.setText("");
                        authen_Num.setText("");
                        // and user can select the reservation.class(Activity)
                        tabs = (MainActivity) getParent();
                        tabs.getTabHost().getTabWidget().getChildAt(0).setEnabled(true);
                        tabs.getTabHost().setCurrentTab(0);

                    } else if (gFlag == 1) {
                        // Stay in here
                    } else if (gFlag == 2) {
                        //If the reservation is not done, it cannot access to reservation_check.class(Activity)
                        tabs = (MainActivity) getParent();
                        tabs.getTabHost().getTabWidget().getChildAt(0).setEnabled(true);
                        tabs.getTabHost().setCurrentTab(0);
                    }
                    mDialog.dismiss();
                }
            }
        });

        return ab.create();
    }

    //예약취소
    public class AsyncReserCancel extends AsyncTask<String, Integer, String> {

        private ProgressDialog mDlg;

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(Reservation_check.this);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("Doing reservation..");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return ns.HttpSendData(params[0], ns.cancel_path);
        }

        protected void onPostExecute(String str) {

            if (str.equals("success")) {
                Log.d("abc", "Reservation cancel success");
                status = 0; // Reservation cancel is success

                pref = getSharedPreferences("pref", MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean("Reservation", false);
                editor.commit();
                editor.clear();

            } else {
                Log.d("abc", "Reservation cancel fail");
                status = 1; // Reservation cancel is fail
            }
            mDialog = createDialog(status);
            mDialog.show();

            mDlg.dismiss();
        }
    }

    //예약확인
    public class AsyncReserCheck extends AsyncTask<String, Integer, String> {

        private ProgressDialog mDlg;

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(Reservation_check.this);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("Doing reservation..");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return ns.HttpSendData(params[0], ns.check_path);
        }

        protected void onPostExecute(String str) {
            try {
                Result_Resv_Info check_rri = ns.setDataFromGson(str);

                if (!check_rri.getParkingslot_id().equals("NULL")) {
                    checkFlag = 0;

                    //The reservation time should be parsing
                    String time[] = check_rri.getResv_starttime().split(":");
                    Log.d("abc","time[1]="+time[1]);
                    String re_time[] = time[1].split(":");

                    if (Integer.parseInt(re_time[0]) < 10) {
                        reservedTime = time[0] + ":" + re_time[0];
                    } else {
                        reservedTime = time[0] + ":"+ re_time[0];
                    }
                    parkingFacility = check_rri.getParkingfacility_id();
                    parkingFloor = check_rri.getParkingslot_floor();
                    parkingZone = check_rri.getParkingslot_zone();
                    parkingSlot = check_rri.getParkingslot_id();
                    authenticationNum = check_rri.getResv_authenticationnum();

                    //For reallocation, we need to update the reservation info into sharedpreference
                    pref = getSharedPreferences("pref", MODE_PRIVATE);
                    editor.putString("park_facility", parkingFacility);
                    editor.putString("park_floor", parkingFloor);
                    editor.putString("park_zone", parkingZone);
                    editor.putString("park_slot", parkingSlot);
                    editor.putString("authen_num", authenticationNum);
                    editor.commit();

                } else {
                    checkFlag = 1;
                    Log.i("abc", "Reservation check fail");

                    reservedTime = pref.getString("reservedTime", "");
                    parkingFacility = pref.getString("park_facility", "");
                    parkingFloor = pref.getString("park_floor", "");
                    parkingZone = pref.getString("park_zone", "");
                    parkingSlot = pref.getString("park_slot", "");
                    authenticationNum = pref.getString("authen_num", "");

                }
                resvTime.setText(reservedTime);
                pFacility.setText(parkingFacility);
                pFloor.setText(parkingFloor);
                pZone.setText(parkingZone);
                pSlot.setText(parkingSlot);
                authen_Num.setText(authenticationNum);

            } catch(Exception ex) {
                ex.printStackTrace();
            }
            mDlg.dismiss();

        }
    }

    public void cancel_reservation(String authen_num) {
        if (aCancel == null) {
            aCancel.execute(authen_num);
        } else {
            aCancel = new AsyncReserCancel();
            aCancel.execute(authen_num);
        }
    }

    public void check_reservation(String phone_num) {
        if (aCheck == null) {
            aCheck.execute(phone_num);
        } else {
            aCheck = new AsyncReserCheck();
            aCheck.execute(phone_num);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "My log onPause()", Toast.LENGTH_SHORT).show();
        if (!resvTime.getText().toString().equals("")) {
            tabs = (MainActivity) getParent();
            tabs.getTabHost().getTabWidget().getChildAt(0).setEnabled(false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "Check_OnResume()", Toast.LENGTH_SHORT).show();
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        ReservationFlag = pref.getBoolean("Reservation", false);

        if (ReservationFlag == false) {
            mDialog = createDialog(2);
            mDialog.show();
        }

        if (!resvTime.getText().toString().equals("")) {
            tabs = (MainActivity) getParent();
            tabs.getTabHost().getTabWidget().getChildAt(0).setEnabled(false);
        }

        //When Reservation_check is success and Reallocation is occured, retry to receive reservation information
        if (checkFlag == 0 || pref.getInt("flag",0)==1) {
            phone_num = pref.getString("phoneNum", "");
            check_reservation(phone_num);
        } else {

            reservedTime = pref.getString("reservedTime", "");
            parkingFacility = pref.getString("park_facility", "");
            parkingFloor = pref.getString("park_floor", "");
            parkingZone = pref.getString("park_zone", "");
            parkingSlot = pref.getString("park_slot", "");
            authenticationNum = pref.getString("authen_num", "");
            ReservationFlag = pref.getBoolean("Reservation", false);

            Log.d("abc", "OnResume_reservedTime" + reservedTime);

            resvTime.setText(reservedTime);
            pFacility.setText(parkingFacility);
            pFloor.setText(parkingFloor);
            pZone.setText(parkingZone);
            pSlot.setText(parkingSlot);
            authen_Num.setText(authenticationNum);
        }
    }
}