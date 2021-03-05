package com.bks.eurosporttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Story(
    val id: Long,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Date,
    val author: String,
    val sport: Sport
):Parcelable