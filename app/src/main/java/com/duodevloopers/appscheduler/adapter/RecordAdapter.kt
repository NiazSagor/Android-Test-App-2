package com.duodevloopers.appscheduler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.util.Utility
import com.duodevloopers.appscheduler.databinding.ScheduleRecordItemLayoutBinding

class RecordAdapter(
    private val appList: List<Schedule>
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: ScheduleRecordItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(s: Schedule) {
            binding.apply {
                appName.text = s.appName
                packageName.text = s.packageName
                scheduleTime.text = Utility.formatMillisecondsIntoDate(s.scheduledAt)
                onTime.text =
                    if (s.inTime) "Scheduler on time : TRUE" else "Scheduler on time : FALSE"
                appLaunched.text =
                    if (s.hasStarted) "App launched : TRUE" else "App launched : FALSE"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = ScheduleRecordItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(appList[position])
    }

    override fun getItemCount(): Int {
        return appList.size
    }

}