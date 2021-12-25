package com.duodevloopers.appscheduler.repo

import androidx.lifecycle.LiveData
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.dao.SchedulerDao

class Repository(
    private val schedulerDao: SchedulerDao
) {

    suspend fun addScheduledApp(schedule: Schedule) {
        schedulerDao.addScheduledApp(schedule)
    }

    suspend fun deleteScheduledApp(id: Int) {
        schedulerDao.deleteSchedule(id)
    }

    suspend fun updateAppScheduleTime(time : Long, id: Int) {
        schedulerDao.updateAppScheduleTime(time, id)
    }

    fun getAllScheduledApps() :LiveData<List<Schedule>> = schedulerDao.getAllApps()

    fun getOnlyScheduledApps() :LiveData<List<Schedule>> = schedulerDao.getScheduledApps()

}