package com.example.fragmentdemoapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fragmentdemoapp.DataTransferManager
import com.example.fragmentdemoapp.R
import com.example.fragmentdemoapp.UserData

class ProfileFragment : Fragment() {

    companion object {
        private const val TAG = "ProfileFragment"
        const val ARG_USER_DATA = "user_data"
        const val RESULT_DATA = "profile_result"
    }

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbStudent: CheckBox
    private lateinit var btnSave: Button
    private lateinit var tvResult: TextView

    private var userData: UserData? = null

    interface OnProfileDataListener {
        fun onProfileDataSaved(userData: UserData)
    }

    private var listener: OnProfileDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
        listener = context as? OnProfileDataListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        arguments?.let {
            userData = it.getParcelable(ARG_USER_DATA)
            Log.d(TAG, "Received user data: $userData")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initViews(view)
        setupUI()
        setListeners()

        return view
    }

    private fun initViews(view: View) {
        etName = view.findViewById(R.id.etName)
        etAge = view.findViewById(R.id.etAge)
        etEmail = view.findViewById(R.id.etEmail)
        cbStudent = view.findViewById(R.id.cbStudent)
        btnSave = view.findViewById(R.id.btnSave)
        tvResult = view.findViewById(R.id.tvResult)
    }

    private fun setupUI() {
        userData?.let {
            etName.setText(it.name)
            etAge.setText(it.age.toString())
            etEmail.setText(it.email)
            cbStudent.isChecked = it.isStudent
        }
    }

    private fun setListeners() {
        btnSave.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val name = etName.text.toString()
        val age = etAge.text.toString().toIntOrNull() ?: 0
        val email = etEmail.text.toString()
        val isStudent = cbStudent.isChecked

        if (name.isNotEmpty() && email.isNotEmpty()) {
            val userData = UserData(name, age, isStudent, email)

            // 保存到共享数据管理器
            DataTransferManager.userData = userData

            // 通知 Activity
            listener?.onProfileDataSaved(userData)

            tvResult.text = "个人信息已保存:\n姓名: $name\n年龄: $age\n邮箱: $email\n学生: ${if (isStudent) "是" else "否"}"
            Toast.makeText(requireContext(), "个人信息保存成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "请填写完整信息", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")

        outState.putString("name", etName.text.toString())
        outState.putString("age", etAge.text.toString())
        outState.putString("email", etEmail.text.toString())
        outState.putBoolean("isStudent", cbStudent.isChecked)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored")

        savedInstanceState?.let {
            etName.setText(it.getString("name", ""))
            etAge.setText(it.getString("age", ""))
            etEmail.setText(it.getString("email", ""))
            cbStudent.isChecked = it.getBoolean("isStudent", false)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
        listener = null
    }
}