package com.example.cmu.cmu_client;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost mTabHost = getTabHost();
        Intent intent;

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        flag = 0;
        flag = pref.getInt("flag", 0);

        intent = new Intent().setClass(this, Reservation.class);
        mTabHost.addTab(mTabHost.newTabSpec("tab_test1")
                .setIndicator("Reservation")
                .setContent(intent)
        );

        intent = new Intent().setClass(this, Reservation_check.class);
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2")
                .setIndicator("My Log")
                .setContent(intent)
        );

        Log.d("abc", "flag: " + flag);
        if(flag == 1) {
            mTabHost.setCurrentTab(1);
        }else{
            mTabHost.setCurrentTab(0);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
    }

}