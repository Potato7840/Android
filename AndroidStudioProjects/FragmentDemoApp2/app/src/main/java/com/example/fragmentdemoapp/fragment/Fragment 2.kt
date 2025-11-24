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

class DataTransferFragment : Fragment() {

    companion object {
        private const val TAG = "DataTransferFragment"
    }

    private lateinit var etDataInput: EditText
    private lateinit var btnSendToActivity: Button
    private lateinit var btnSendToFragment: Button
    private lateinit var tvReceivedData: TextView
    private lateinit var tvSharedData: TextView
    private lateinit var btnRefresh: Button

    private var dataFromActivity: String? = null

    interface OnDataTransferListener {
        fun onDataSentToActivity(data: String)
        fun onRequestSendToOtherFragment(data: String)
    }

    private var listener: OnDataTransferListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
        listener = context as? OnDataTransferListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        arguments?.let {
            dataFromActivity = it.getString("data_from_activity")
            Log.d(TAG, "Received data from activity: $dataFromActivity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_data_transfer, container, false)

        initViews(view)
        setupUI()
        setListeners()

        return view
    }

    private fun initViews(view: View) {
        etDataInput = view.findViewById(R.id.etDataInput)
        btnSendToActivity = view.findViewById(R.id.btnSendToActivity)
        btnSendToFragment = view.findViewById(R.id.btnSendToFragment)
        tvReceivedData = view.findViewById(R.id.tvReceivedData)
        tvSharedData = view.findViewById(R.id.tvSharedData)
        btnRefresh = view.findViewById(R.id.btnRefresh)
    }

    private fun setupUI() {
        dataFromActivity?.let {
            tvReceivedData.text = "从Activity接收的数据: $it"
        }

        updateSharedDataDisplay()
    }

    private fun setListeners() {
        btnSendToActivity.setOnClickListener {
            val data = etDataInput.text.toString()
            if (data.isNotEmpty()) {
                listener?.onDataSentToActivity(data)
                Toast.makeText(requireContext(), "数据已发送到Activity", Toast.LENGTH_SHORT).show()
            }
        }

        btnSendToFragment.setOnClickListener {
            val data = etDataInput.text.toString()
            if (data.isNotEmpty()) {
                DataTransferManager.sharedData = data
                listener?.onRequestSendToOtherFragment(data)
                updateSharedDataDisplay()
                Toast.makeText(requireContext(), "数据已发送到其他Fragment", Toast.LENGTH_SHORT).show()
            }
        }

        btnRefresh.setOnClickListener {
            updateSharedDataDisplay()
        }
    }

    private fun updateSharedDataDisplay() {
        tvSharedData.text = "共享数据: ${DataTransferManager.sharedData}"
    }

    fun updateReceivedData(data: String) {
        tvReceivedData.text = "从其他Fragment接收的数据: $data"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putString("input_data", etDataInput.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored")
        savedInstanceState?.let {
            etDataInput.setText(it.getString("input_data", ""))
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
        listener = null
    }
}