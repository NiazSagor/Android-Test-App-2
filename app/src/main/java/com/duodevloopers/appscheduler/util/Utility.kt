package com.duodevloopers.appscheduler.util

import android.text.format.DateFormat
import java.util.*

class Utility {

    companion object {
        fun formatMillisecondsIntoDate(time: Long): String {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            return DateFormat.format("dd-MM-yyyy hh:mm aa", calendar).toString()
        }
    }


}