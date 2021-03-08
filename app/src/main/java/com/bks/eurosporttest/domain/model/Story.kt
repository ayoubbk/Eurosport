package com.bks.eurosporttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Story(
    override val id: Long,
    override val title: String,
    val teaser: String,
    val image: String,
    override val date: Date,
    val author: String,
    override val sport: Sport
):Parcelable, FeaturedItem