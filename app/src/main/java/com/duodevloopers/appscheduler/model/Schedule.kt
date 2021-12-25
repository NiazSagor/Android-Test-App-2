package com.duodevloopers.appscheduler.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_scheduler_table")
class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val appName: String = "N/A",
    val packageName: String,
    var scheduledAt: Long,
    val inTime: Boolean,
    val hasStarted: Boolean
)