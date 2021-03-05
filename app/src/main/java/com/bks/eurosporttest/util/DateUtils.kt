package com.bks.eurosporttest.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    fun timestampToDate(timestamp: Double): Date {
        val time = timestamp * 1000L // Fraction in nano seconds 1588224445.007
        return Date(time.toLong())
    }

    fun dateToTimestamp(date: Date): Double {
        return date.time.toDouble()
    }

    fun dateToString(date: Date): String {
        return sdf.format(date)
    }

    fun stringToDate(string: String): Date {
        return sdf.parse(string)
            ?: throw NullPointerException("Could not convert date string to Date object.")
    }

}