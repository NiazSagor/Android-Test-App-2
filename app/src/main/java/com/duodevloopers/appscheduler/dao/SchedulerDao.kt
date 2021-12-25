package com.duodevloopers.appscheduler.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duodevloopers.appscheduler.model.Schedule

@Dao
interface SchedulerDao {

    @Insert
    suspend fun addScheduledApp(schedule: Schedule)

    @Query("SELECT * FROM app_scheduler_table ORDER BY scheduledAt DESC")
    fun getAllApps(): LiveData<List<Schedule>>

    @Query("SELECT * FROM app_scheduler_table WHERE hasStarted = 0 ORDER BY scheduledAt DESC")
    fun getScheduledApps(): LiveData<List<Schedule>>

    @Query("UPDATE app_scheduler_table SET hasStarted =:hasStarted WHERE id =:id")
    suspend fun updateAppStatus(hasStarted: Boolean, id: Int)

    @Query("UPDATE app_scheduler_table SET scheduledAt =:time WHERE id =:id")
    suspend fun updateAppScheduleTime(time : Long, id: Int)

    @Query("DELETE FROM app_scheduler_table WHERE id=:id")
    suspend fun deleteSchedule(id: Int)

    @Query("UPDATE app_scheduler_table SET inTime = 1 WHERE id=:id")
    suspend fun updateAppIfInTime(id: Int)

}