package com.duodevloopers.appscheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.duodevloopers.appscheduler.db.SchedulerDatabase
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    private val allScheduledApps: LiveData<List<Schedule>>

    private val onlyScheduledApps: LiveData<List<Schedule>>

    init {
        val schedulerDao = SchedulerDatabase.getDatabase(application).schedulerDao()
        repository = Repository(schedulerDao)
        allScheduledApps = repository.getAllScheduledApps()
        onlyScheduledApps = repository.getOnlyScheduledApps()
    }

    fun addScheduledApp(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addScheduledApp(schedule)
        }
    }

    fun deleteScheduledApp(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScheduledApp(id)
        }
    }

    fun updateAppScheduleTime(time: Long, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAppScheduleTime(time, id)
        }
    }

    fun getAllScheduledApps() = allScheduledApps

    fun getOnlyScheduledApps() = onlyScheduledApps

}