package com.example.fragmentdemoapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fragmentdemoapp.R

class LifecycleFragment : Fragment() {

    companion object {
        private const val TAG = "LifecycleFragment"
    }

    private lateinit var tvLogs: TextView
    private val logs = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addLog("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addLog("onCreateView")
        val view = inflater.inflate(R.layout.fragment_lifecycle, container, false)
        tvLogs = view.findViewById(R.id.tvLogs)
        updateLogDisplay()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLog("onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        addLog("onViewStateRestored")

        savedInstanceState?.let {
            val savedLogs = it.getString("logs", "")
            if (savedLogs.isNotEmpty()) {
                logs.clear()
                logs.append(savedLogs)
                updateLogDisplay()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        addLog("onStart")
    }

    override fun onResume() {
        super.onResume()
        addLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        addLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        addLog("onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        addLog("onSaveInstanceState")
        outState.putString("logs", logs.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addLog("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        addLog("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        addLog("onDetach")
    }

    private fun addLog(log: String) {
        val timestamp = System.currentTimeMillis()
        logs.append("$timestamp: $log\n")
        Log.d(TAG, log)

        if (::tvLogs.isInitialized) {
            updateLogDisplay()
        }
    }

    private fun updateLogDisplay() {
        tvLogs.text = logs.toString()
    }
}