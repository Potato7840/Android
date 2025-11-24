package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AirplanModeReceiver airplanModeReciever = new AirplanModeReceiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLAN_MODE");
        registerReceiver(airplanModeReciever,intentFilter)


    }

    class AirplanModeReceiver extends BroadcastReceiver{
    @Override
        public void onReceive(Context context, Intent intent){
        Toast.makeText(context, text:"飞行模式",toast.LENGTH_L)
    }
    }
}