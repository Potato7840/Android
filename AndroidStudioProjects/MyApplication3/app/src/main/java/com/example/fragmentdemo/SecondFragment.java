package com.example.fragmentdemo;

import static android.os.Build.VERSION_CODES_FULL.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.fragmentdemo.R;

public class SecondFragment extends Fragment {

    public SecondFragment() {
        // 必需的空公共构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 填充布局
        return inflater.inflate(R, container, false);
    }
}