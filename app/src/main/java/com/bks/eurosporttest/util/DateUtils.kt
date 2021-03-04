package com.bks.eurosporttest.util

import java.sql.Timestamp
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

    // Ex: March 4, 2021
    fun dateToString(date: Date): String {
        return sdf.format(date)
    }

    fun stringToDate(string: String): Date {
        return sdf.parse(string)
            ?: throw NullPointerException("Could not convert date string to Date object.")
    }

}