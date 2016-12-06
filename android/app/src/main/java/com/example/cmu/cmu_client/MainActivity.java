package com.example.cmu.cmu_client;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    private int flag; // flag that determines which tab to be opened.

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
        // flag = 0: Reservation, flag = 1: My log
        if(flag == 1) {
            mTabHost.setCurrentTab(1);
        }else{
            mTabHost.setCurrentTab(0);
        }
    }
}