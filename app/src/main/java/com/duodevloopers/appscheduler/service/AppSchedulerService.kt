package com.duodevloopers.appscheduler.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.duodevloopers.appscheduler.*
import com.duodevloopers.appscheduler.br.MyBroadcastReceiver
import com.duodevloopers.appscheduler.dao.SchedulerDao
import com.duodevloopers.appscheduler.db.SchedulerDatabase
import com.duodevloopers.appscheduler.model.Schedule
import com.duodevloopers.appscheduler.util.Utility
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class AppSchedulerService : Service() {

    private lateinit var schedulerDao: SchedulerDao

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()

        Log.d("Service", "onCreate: ")

        if (Build.VERSION.SDK_INT >= 26) {
            val notification: NotificationCompat.Builder = NotificationCompat.Builder(
                this,
                "push-notification-channel-id"
            )
                .setContentTitle("App Scheduler")
                .setContentText("App is running")
                .setSmallIcon(R.mipmap.ic_launcher_round)

            startForeground(1, notification.build())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        context = this

        Log.d("Service", "onStartCommand: ")

        val timer = Timer()

        schedulerDao = SchedulerDatabase.getDatabase(this).schedulerDao()

        val apps: LiveData<List<Schedule>> = schedulerDao.getAllApps()

        apps.observeForever(Observer {

            timer.schedule(object : TimerTask() {
                override fun run() {

                    for (s in it) {
                        if ((Utility.formatMillisecondsIntoDate(s.scheduledAt) == Utility.formatMillisecondsIntoDate(
                                System.currentTimeMillis()
                            )) && !s.hasStarted
                        ) {

                            sendBroadcast(
                                Intent(context, MyBroadcastReceiver::class.java)
                                    .putExtra("package_name", s.packageName)
                                    .putExtra("app_id", s.id)
                            )

                            Log.d("Service", "found an app to run")

                            GlobalScope.launch {
                                schedulerDao.updateAppIfInTime(s.id)
                            }

                        } else {
                            Log.d("Service", "no app")
                        }
                    }

                }

            }, 5000, 5000)


        })

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}