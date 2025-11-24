package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etContent;
    private TextView tvWelcome;
    private Button btnSaveToFile, btnLoadFromFile, btnSaveToDatabase;
    private SharedPreferences sharedPreferences;
    private MyDbHelper dbHelper;

    private static final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        dbHelper = new MyDbHelper(this);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        setupClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从SharedPreferences读取用户名并更新标题
        String userName = sharedPreferences.getString("user_name", "Guest");
        tvWelcome.setText("欢迎 " + userName + " 使用记事本");

        // 如果开启了自动保存，自动保存当前内容
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave && !etContent.getText().toString().trim().isEmpty()) {
            saveToFile();
        }
    }

    private void initViews() {
        etContent = findViewById(R.id.etContent);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDatabase = findViewById(R.id.btnSaveToDatabase);
    }

    private void setupClickListeners() {
        btnSaveToFile.setOnClickListener(v -> saveToFile());

        btnLoadFromFile.setOnClickListener(v -> loadFromFile());

        btnSaveToDatabase.setOnClickListener(v -> saveToDatabase());
    }

    private void saveToFile() {
        String text = etContent.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String text = new String(buffer);
            etContent.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveToDatabase() {
        String content = etContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（取前10个字符）
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        // 修复：使用正确的 ContentValues 创建方式
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, title);
        values.put(MyDbHelper.COLUMN_CONTENT, content);
        values.put(MyDbHelper.COLUMN_TIME, time);

        long result = dbHelper.getWritableDatabase().insert(
                MyDbHelper.TABLE_RECORDS,
                null,
                values
        );

        if (result != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            etContent.setText("");
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}