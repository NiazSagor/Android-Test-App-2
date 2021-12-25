package com.duodevloopers.appscheduler.fragment

import android.app.TimePickerDialog
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.duodevloopers.appscheduler.R
import com.duodevloopers.appscheduler.adapter.AppAdapter
import com.duodevloopers.appscheduler.databinding.FragmentAllAppsBinding
import com.duodevloopers.appscheduler.listener.OnClickListener
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.viewmodel.MainActivityViewModel
import java.util.*
import kotlin.collections.ArrayList

class AllAppsFragment : Fragment(), OnClickListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentAllAppsBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAppsBinding.inflate(layoutInflater)

        requireActivity().title = "Installed Apps"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appList: MutableList<Schedule> = ArrayList()

        val pm = requireActivity().packageManager
        val list = pm.getInstalledApplications(0)

        for (app in list) {

            if (app.flags and ApplicationInfo.FLAG_SYSTEM !== 0) {

            } else {
                appList.add(
                    Schedule(
                        0,
                        pm.getApplicationLabel(app).toString(),
                        app.packageName,
                        0,
                        false,
                        hasStarted = false
                    )
                )
            }


        }

        val adapter = AppAdapter(appList, "all")
        binding.appList.adapter = adapter
        adapter.setOnClickListener(this)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_allAppsFragment_to_scheduledAppsFragment)
        }

        binding.record.setOnClickListener {
            findNavController().navigate(R.id.action_allAppsFragment_to_recordFragment)
        }
    }

    private lateinit var selectedApp: Schedule

    override fun onClick(app: Schedule) {

        selectedApp = app

        val c: Calendar = Calendar.getInstance()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)

        TimePickerDialog(
            requireActivity(), this, hour, minute,
            DateFormat.is24HourFormat(requireActivity())
        ).show()

        Log.d("SetTimer", selectedApp.appName + " is clicked")

    }

    override fun onCancel(id: Int) {

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        if (c.timeInMillis > System.currentTimeMillis()) {
            selectedApp.scheduledAt = c.timeInMillis
            mainActivityViewModel.addScheduledApp(selectedApp)
        } else {
            Toast.makeText(
                requireContext(),
                "Schedule time can not be less than current time",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}