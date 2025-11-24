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

public class PersonInfoFragment extends Fragment {

    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    private EditText etName, etAge;
    private Button btnSubmit;
    private TextView tvResult;

    public PersonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etName);
        etAge = view.findViewById(R.id.etAge);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvResult = view.findViewById(R.id.tvResult);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInfo();
            }
        });

        // 恢复保存的状态
        if (savedInstanceState != null) {
            etName.setText(savedInstanceState.getString(KEY_NAME, ""));
            etAge.setText(savedInstanceState.getString(KEY_AGE, ""));
        }

        // 接收从 Activity 传递的数据
        Bundle args = getArguments();
        if (args != null) {
            String initialData = args.getString("initial_data", "");
            if (!initialData.isEmpty()) {
                tvResult.setText("从 Activity 接收的数据: " + initialData);
            }
        }
    }

    private void submitInfo() {
        String name = etName.getText().toString();
        String age = etAge.getText().toString();

        String result = "姓名: " + name + "\n年龄: " + age;
        tvResult.setText(result);

        // 将数据传回 Activity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onDataFromFragment("PersonInfo", result);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME, etName.getText().toString());
        outState.putString(KEY_AGE, etAge.getText().toString());
        System.out.println("PersonInfoFragment: 状态已保存");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            System.out.println("PersonInfoFragment: 状态已恢复");
        }
    }
}