package com.example.fragmentdemoapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fragmentdemoapp.R

class SettingsFragment : Fragment() {

    companion object {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var switchNotification: Switch
    private lateinit var radioGroupTheme: RadioGroup
    private lateinit var seekBarFont: SeekBar
    private lateinit var tvFontSize: TextView
    private lateinit var btnSaveSettings: Button
    private lateinit var tvAppInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        initViews(view)
        setListeners()
        setupAppInfo()

        return view
    }

    private fun initViews(view: View) {
        switchNotification = view.findViewById(R.id.switchNotification)
        radioGroupTheme = view.findViewById(R.id.radioGroupTheme)
        seekBarFont = view.findViewById(R.id.seekBarFont)
        tvFontSize = view.findViewById(R.id.tvFontSize)
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings)
        tvAppInfo = view.findViewById(R.id.tvAppInfo)

        // 设置初始值
        tvFontSize.text = "字体大小: ${seekBarFont.progress}"
    }

    private fun setListeners() {
        seekBarFont.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvFontSize.text = "字体大小: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSaveSettings.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        val notificationEnabled = switchNotification.isChecked
        val theme = when (radioGroupTheme.checkedRadioButtonId) {
            R.id.radioLight -> "浅色"
            R.id.radioDark -> "深色"
            R.id.radioAuto -> "自动"
            else -> "未知"
        }
        val fontSize = seekBarFont.progress

        val settingsSummary = """
            |设置已保存:
            |• 通知: ${if (notificationEnabled) "开启" else "关闭"}
            |• 主题: $theme
            |• 字体大小: $fontSize
        """.trimMargin()

        Toast.makeText(requireContext(), settingsSummary, Toast.LENGTH_LONG).show()
    }

    private fun setupAppInfo() {
        val appInfo = """
            |应用信息
            |版本: 1.0.0
            |构建时间: 2024
            |功能介绍:
            |• Fragment 切换
            |• Bundle 数据传输
            |• 状态保存与恢复
        """.trimMargin()

        tvAppInfo.text = appInfo
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")

        outState.putBoolean("notification", switchNotification.isChecked)
        outState.putInt("theme", radioGroupTheme.checkedRadioButtonId)
        outState.putInt("font_size", seekBarFont.progress)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored")

        savedInstanceState?.let {
            switchNotification.isChecked = it.getBoolean("notification", false)
            radioGroupTheme.check(it.getInt("theme", R.id.radioLight))
            val fontSize = it.getInt("font_size", 14)
            seekBarFont.progress = fontSize
            tvFontSize.text = "字体大小: $fontSize"
        }
    }
}