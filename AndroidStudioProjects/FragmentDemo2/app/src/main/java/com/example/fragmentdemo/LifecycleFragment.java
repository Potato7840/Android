package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LifecycleFragment extends Fragment {

    private TextView tvLog;
    private StringBuilder logBuilder = new StringBuilder();

    public LifecycleFragment() {
        // Required empty public constructor
    }

    private void addLog(String methodName) {
        String log = methodName + "() 被调用 - 时间: " + System.currentTimeMillis() + "\n";
        logBuilder.append(log);

        if (tvLog != null) {
            tvLog.setText(logBuilder.toString());
        }

        System.out.println("LifecycleFragment: " + log);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addLog("onCreateView");
        return inflater.inflate(R.layout.fragment_lifecycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addLog("onViewCreated");

        tvLog = view.findViewById(R.id.tvLog);
        tvLog.setText(logBuilder.toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLog("onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        addLog("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        addLog("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        addLog("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        addLog("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addLog("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addLog("onDestroy");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        addLog("onSaveInstanceState");
        outState.putString("log_data", logBuilder.toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        addLog("onViewStateRestored");

        if (savedInstanceState != null) {
            String savedLog = savedInstanceState.getString("log_data", "");
            if (!savedLog.isEmpty()) {
                logBuilder = new StringBuilder(savedLog);
                if (tvLog != null) {
                    tvLog.setText(logBuilder.toString());
                }
            }
        }
    }
}