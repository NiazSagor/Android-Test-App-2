package com.duodevloopers.appscheduler.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.duodevloopers.appscheduler.adapter.AppAdapter
import com.duodevloopers.appscheduler.databinding.FragmentScheduledAppsBinding
import com.duodevloopers.appscheduler.listener.OnClickListener
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.viewmodel.MainActivityViewModel
import java.util.*

class ScheduledAppsFragment : Fragment(), OnClickListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentScheduledAppsBinding

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
        binding = FragmentScheduledAppsBinding.inflate(layoutInflater)

        requireActivity().title = "Upcoming Scheduled Apps"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel.getOnlyScheduledApps().observe(viewLifecycleOwner, Observer {
            val adapter = AppAdapter(it, "scheduled")
            binding.appList.adapter = adapter
            adapter.setOnClickListener(this)
        })
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
    }

    override fun onCancel(id: Int) {
        mainActivityViewModel.deleteScheduledApp(id)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        if (c.timeInMillis > System.currentTimeMillis()) {
            mainActivityViewModel.updateAppScheduleTime(c.timeInMillis, selectedApp.id)
        } else {
            Toast.makeText(
                requireContext(),
                "Schedule time can not be less than current time",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}