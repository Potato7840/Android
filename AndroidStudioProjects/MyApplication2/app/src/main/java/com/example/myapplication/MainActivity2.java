package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private EditText editText;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = findViewById(R.id.editText);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            String inputText = editText.getText().toString().trim();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("input_text", inputText);
            setResult(RESULT_OK, resultIntent);
            finish(); // 关闭当前页面，返回主页面
        });
    }
}