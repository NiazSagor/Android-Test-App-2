package com.duodevloopers.appscheduler.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duodevloopers.appscheduler.dao.SchedulerDao
import com.duodevloopers.appscheduler.model.Schedule

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class SchedulerDatabase : RoomDatabase() {

    abstract fun schedulerDao(): SchedulerDao

    companion object {

        @Volatile
        private var INSTANCE: SchedulerDatabase? = null

        fun getDatabase(context: Context): SchedulerDatabase {

            val instance = INSTANCE

            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    SchedulerDatabase::class.java,
                    "scheduler_database"
                ).build()
                INSTANCE = newInstance
                return newInstance
            }

        }

    }

}