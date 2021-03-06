package com.bks.eurosporttest.util

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object EuroDateUtils {

    private val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    fun timestampToDate(timestamp: Double): Date {
        val time = timestamp * 1000L // Fraction in nano seconds 1588224445.007
        return Date(time.toLong())
    }

    fun dateToTimestamp(date: Date): Double {
        return date.time.toDouble()
    }

    fun getFormattedDate(date: Date): String {
        val dateFormat = DateFormat.getDateInstance() // use DateFormat to get date in phone Locale

        val time = date.time
        val timeInMillis = Calendar.getInstance().timeInMillis
        return DateUtils.getRelativeTimeSpanString(time, timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
    }


}