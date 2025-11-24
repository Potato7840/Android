package com.example.notepadapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvContent = findViewById(R.id.tvDetailContent);
        TextView tvTime = findViewById(R.id.tvDetailTime);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String time = getIntent().getStringExtra("time");

        tvTitle.setText(title);
        tvContent.setText(content);
        tvTime.setText("创建时间: " + time);
    }
}