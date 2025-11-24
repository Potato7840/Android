package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataTransferFragment extends Fragment {

    private TextView tvData;
    private Button btnSendToOther;

    public DataTransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_transfer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvData = view.findViewById(R.id.tvData);
        btnSendToOther = view.findViewById(R.id.btnSendToOther);

        // 接收从 Activity 传递的数据
        Bundle args = getArguments();
        if (args != null) {
            String dataFromActivity = args.getString("data_from_activity", "");
            if (!dataFromActivity.isEmpty()) {
                tvData.setText("从 Activity 接收: " + dataFromActivity);
            }
        }

        btnSendToOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送数据到其他 Fragment（通过 Activity 中转）
                if (getActivity() instanceof MainActivity) {
                    String data = "来自 DataTransferFragment 的数据 - 时间: " + System.currentTimeMillis();
                    ((MainActivity) getActivity()).sendDataToOtherFragment(data);
                    tvData.setText("已发送数据: " + data);
                }
            }
        });
    }
}