package com.bks.eurosporttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sport (
    val id: Int,
    val name: String
):Parcelable