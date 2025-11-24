package com.example.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private PersonInfoFragment personInfoFragment;
    private DataTransferFragment dataTransferFragment;
    private LifecycleFragment lifecycleFragment;
    private SettingsFragment settingsFragment;

    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private int currentFragmentId = R.id.rbPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity: onCreate");

        initViews();
        initFragments();

        // 恢复状态
        if (savedInstanceState != null) {
            currentFragmentId = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT, R.id.rbPersonInfo);
            radioGroup.check(currentFragmentId);
            System.out.println("MainActivity: 状态已恢复，当前Fragment ID: " + currentFragmentId);
        } else {
            showFragment(personInfoFragment);
        }

        // 演示 Activity → Activity 数据传输
        demonstrateActivityToActivity();
    }

    private void initViews() {
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragments() {
        personInfoFragment = new PersonInfoFragment();
        dataTransferFragment = new DataTransferFragment();
        lifecycleFragment = new LifecycleFragment();
        settingsFragment = new SettingsFragment();

        // 向 Fragment 传递初始数据
        Bundle args = new Bundle();
        args.putString("initial_data", "来自 MainActivity 的初始数据");
        personInfoFragment.setArguments(args);

        Bundle args2 = new Bundle();
        args2.putString("data_from_activity", "Activity 向 Fragment 传递的数据");
        dataTransferFragment.setArguments(args2);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        currentFragmentId = checkedId;

        switch (checkedId) {
            case R.id.rbPersonInfo:
                showFragment(personInfoFragment);
                break;
            case R.id.rbDataTransfer:
                showFragment(dataTransferFragment);
                break;
            case R.id.rbLifecycle:
                showFragment(lifecycleFragment);
                break;
            case R.id.rbSettings:
                showFragment(settingsFragment);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    // Fragment 向 Activity 返回数据的回调
    public void onDataFromFragment(String fragmentName, String data) {
        System.out.println("从 " + fragmentName + " 接收到数据: " + data);
    }

    // Fragment 之间数据传输（通过 Activity 中转）
    public void sendDataToOtherFragment(String data) {
        if (settingsFragment != null && settingsFragment.isAdded()) {
            settingsFragment.onDataReceived(data);
        }
        System.out.println("中转数据到其他 Fragment: " + data);
    }

    // 演示 Activity → Activity 数据传输
    private void demonstrateActivityToActivity() {
        // 这里只是演示，实际调用可以在按钮点击事件中
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("user_name", "张三");
        intent.putExtra("user_age", 25);
        intent.putExtra("is_student", false);
        // startActivity(intent); // 需要时取消注释
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_FRAGMENT, currentFragmentId);
        System.out.println("MainActivity: onSaveInstanceState - 保存当前Fragment ID: " + currentFragmentId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("MainActivity: onRestoreInstanceState");
    }

    // 生命周期方法用于日志记录
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity: onDestroy");
    }
}