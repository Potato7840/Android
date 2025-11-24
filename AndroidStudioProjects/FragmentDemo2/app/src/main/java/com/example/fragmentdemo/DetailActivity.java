package com.example.fragmentdemo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 接收从 MainActivity 传递的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("user_name", "");
            int userAge = extras.getInt("user_age", 0);
            boolean isStudent = extras.getBoolean("is_student", false);

            displayUserInfo(userName, userAge, isStudent);
        }
    }

    private void displayUserInfo(String name, int age, boolean isStudent) {
        TextView tvInfo = findViewById(R.id.tvUserInfo);

        String info = "用户信息:\n" +
                "姓名: " + name + "\n" +
                "年龄: " + age + "\n" +
                "是否学生: " + (isStudent ? "是" : "否");

        tvInfo.setText(info);

        System.out.println("DetailActivity: 接收到的用户信息 - " +
                "姓名:" + name + ", 年龄:" + age + ", 是否学生:" + isStudent);
    }
}