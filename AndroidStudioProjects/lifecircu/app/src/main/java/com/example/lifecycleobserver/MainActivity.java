package com.example.lifecycleobserver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecycleobserver.R ;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        setupButtons();
    }

    private void setupButtons() {
        Button btnOpenSecond = findViewById(R.id.btn_open_second);
        Button btnOpenDialog = findViewById(R.id.btn_open_dialog);
        Button btnFinish = findViewById(R.id.btn_finish);

        btnOpenSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.lifecycleobserver.SecondActivity.class);
            startActivity(intent);
        });

        btnOpenDialog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);
        });

        btnFinish.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}