package com.duodevloopers.appscheduler.listener

import com.duodevloopers.appscheduler.model.Schedule

interface OnClickListener {
    fun onClick(app: Schedule)
    fun onCancel(id: Int)
}