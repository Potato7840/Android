package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox cbAutoSave;
    private EditText etUserName, etPassword;
    private Button btnSaveSettings, btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 加载保存的设置
        loadSettings();

        setupClickListeners();
    }

    private void initViews() {
        cbAutoSave = findViewById(R.id.cbAutoSave);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("password", "");

        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPassword.setText(password);
    }

    private void setupClickListeners() {
        btnSaveSettings.setOnClickListener(v -> saveSettings());

        btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "登录功能演示", Toast.LENGTH_SHORT).show();
            // 这里可以添加登录逻辑
        });
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", cbAutoSave.isChecked());
        editor.putString("user_name", etUserName.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "设置保存成功", Toast.LENGTH_SHORT).show();
    }
}