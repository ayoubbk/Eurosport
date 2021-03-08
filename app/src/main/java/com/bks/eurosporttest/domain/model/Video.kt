package com.bks.eurosporttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Video (
    override val id: Long,
    override val title: String,
    val thumb: String,
    val url: String,
    override val date: Date,
    override val sport: Sport,
    val views: Int
):Parcelable, FeaturedItem
