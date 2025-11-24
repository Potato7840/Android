package com.example.fragmentdemoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentdemoapp.fragment.DataTransferFragment
import com.example.fragmentdemoapp.fragment.ProfileFragment

class MainActivity : AppCompatActivity(),
    ProfileFragment.OnProfileDataListener,
    DataTransferFragment.OnDataTransferListener {

    private lateinit var radioGroup: RadioGroup

    private var currentFragmentTag: String = "profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate")

        initViews()
        setupFragment(savedInstanceState)
    }

    private fun initViews() {
        radioGroup = findViewById(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioProfile -> switchFragment(ProfileFragment::class.java, "profile")
                R.id.radioDataTransfer -> switchFragment(DataTransferFragment::class.java, "data_transfer")
                R.id.radioLifecycle -> switchFragment(LifecycleFragment::class.java, "lifecycle")
                R.id.radioSettings -> switchFragment(SettingsFragment::class.java, "settings")
            }
        }
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // 首次创建，显示默认Fragment
            switchFragment(ProfileFragment::class.java as Class<T>, "profile")
            radioGroup.check(R.id.radioProfile)

            // 向ProfileFragment传递初始数据
            val initialUserData = UserData("默认用户", 25, true, "default@email.com")
            val bundle = Bundle().apply {
                putParcelable(ProfileFragment.ARG_USER_DATA, initialUserData)
            }

            supportFragmentManager.findFragmentByTag("profile")?.arguments = bundle
        } else {
            // 恢复状态
            currentFragmentTag = savedInstanceState.getString("current_fragment", "profile")
            when (currentFragmentTag) {
                "profile" -> radioGroup.check(R.id.radioProfile)
                "data_transfer" -> radioGroup.check(R.id.radioDataTransfer)
                "lifecycle" -> radioGroup.check(R.id.radioLifecycle)
                "settings" -> radioGroup.check(R.id.radioSettings)
            }
        }
    }

    private fun switchFragment(fragmentClass: Class<T>, tag: String) {
        currentFragmentTag = tag

        val fragment = supportFragmentManager.findFragmentByTag(tag) ?:
        when (fragmentClass) {
            ProfileFragment::class.java -> {
                val fragment = ProfileFragment()
                // 传递数据到ProfileFragment
                val bundle = Bundle().apply {
                    putParcelable(ProfileFragment.ARG_USER_DATA, DataTransferManager.userData)
                }
                fragment.arguments = bundle
                fragment
            }
            DataTransferFragment::class.java -> {
                val fragment = DataTransferFragment()
                // 传递数据到DataTransferFragment
                val bundle = Bundle().apply {
                    putString("data_from_activity", "Hello from Activity!")
                }
                fragment.arguments = bundle
                fragment
            }
            else -> fragmentClass.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit
    }

    annotation class T

    // ProfileFragment.OnProfileDataListener
    override fun onProfileDataSaved(userData: UserData) {
        Log.d("MainActivity", "Received user data from ProfileFragment: $userData")

        // 场景 A: 启动DetailActivity并传递数据
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("user_data", userData)
        }
        startActivity(intent)
    }

    // DataTransferFragment.OnDataTransferListener
    override fun onDataSentToActivity(data: String) {
        Log.d("MainActivity", "Received data from DataTransferFragment: $data")
        // 处理从Fragment发送到Activity的数据
        Toast.makeText(this, "从Fragment接收的数据: $data", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestSendToOtherFragment(data: String) {
        Log.d("MainActivity", "Request to send data to other fragment: $data")

        // 场景 C: Fragment → Fragment 通过Activity中转
        val targetFragment = supportFragmentManager.findFragmentByTag("data_transfer")
        if (targetFragment is DataTransferFragment) {
            targetFragment.updateReceivedData(data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState")
        outState.putString("current_fragment", currentFragmentTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MainActivity", "onRestoreInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
}