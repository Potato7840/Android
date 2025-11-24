package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private TextView tvReceivedData;
    private EditText etSettingsInput;
    private Button btnSaveSettings;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvReceivedData = view.findViewById(R.id.tvReceivedData);
        etSettingsInput = view.findViewById(R.id.etSettingsInput);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String settings = etSettingsInput.getText().toString();
                if (!settings.isEmpty()) {
                    // 将设置数据传回 Activity
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).onDataFromFragment("Settings", settings);
                    }
                }
            }
        });
    }

    // 接收从其他 Fragment 传来的数据
    public void onDataReceived(String data) {
        if (tvReceivedData != null) {
            tvReceivedData.setText("接收到的数据: " + data);
        }
    }
}