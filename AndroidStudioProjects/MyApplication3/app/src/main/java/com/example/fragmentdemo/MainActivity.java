package com.example.fragmentdemo;

import static android.os.Build.VERSION_CODES_FULL.R;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragmentdemo.FirstFragment;
import com.example.fragmentdemo.R;

public class MainActivity extends AppCompatActivity {

    private Button btnFragment1, btnFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R);

        // 初始化视图
        initViews();

        // 设置按钮点击监听器
        setClickListeners();

        // 默认显示第一个Fragment
        loadFragment(new FirstFragment());
    }

    private void initViews() {
        btnFragment1 = findViewById(R);
        btnFragment2 = findViewById(R);
    }

    private void setClickListeners() {
        btnFragment1.setOnClickListener(v -> {
            // 切换到第一个Fragment
            loadFragment(new FirstFragment());
        });

        btnFragment2.setOnClickListener(v -> {
            // 切换到第二个Fragment
            loadFragment(new SecondFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        // 创建Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 替换Fragment容器中的内容
        transaction.replace(R, fragment);

        // 添加到返回栈（可选）
        transaction.addToBackStack(null);

        // 提交事务
        transaction.commit();
    }
}