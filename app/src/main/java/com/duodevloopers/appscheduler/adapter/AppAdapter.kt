package com.duodevloopers.appscheduler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duodevloopers.appscheduler.listener.OnClickListener
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.util.Utility
import com.duodevloopers.appscheduler.databinding.AppItemLayoutBinding

class AppAdapter(
    private val apps: List<Schedule>,
    private val flag: String
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    private lateinit var onClickListener: OnClickListener

    inner class AppViewHolder(
        private val binding: AppItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.apply {

                if (flag == "scheduled") {
                    scheduleApp.text = "Reschedule"
                    cancelSchedule.visibility = View.VISIBLE
                    cancelSchedule.setOnClickListener {
                        onClickListener.onCancel(schedule.id)
                    }
                    scheduleTime.visibility = View.VISIBLE
                    scheduleTime.text = Utility.formatMillisecondsIntoDate(schedule.scheduledAt)
                } else {
                    if (schedule.scheduledAt == 0L) scheduleTime.visibility = View.GONE
                    cancelSchedule.visibility = View.GONE
                }

                packageName.text = schedule.packageName
                appName.text = schedule.appName

                scheduleApp.setOnClickListener {
                    onClickListener.onClick(schedule)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = AppItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

}