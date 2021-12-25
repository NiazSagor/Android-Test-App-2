package com.duodevloopers.appscheduler.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.duodevloopers.appscheduler.db.SchedulerDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val schedulerDao = context?.let { SchedulerDatabase.getDatabase(it).schedulerDao() }

        val s = intent?.getStringExtra("package_name")
        val id = intent?.getIntExtra("app_id", 0)

        GlobalScope.launch {
            id?.let { schedulerDao?.updateAppStatus(true, it) }
        }

        context?.startActivity(
            s?.let { context.packageManager?.getLaunchIntentForPackage(it) }
        )
    }
}