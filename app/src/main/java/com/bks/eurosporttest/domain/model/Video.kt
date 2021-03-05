package com.bks.eurosporttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Video (
    val id: Long,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Date,
    val sport: Sport,
    val views: Int
):Parcelable
