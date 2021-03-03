package com.bks.eurosporttest.domain.model

import java.util.*

data class Video (
    val id: Long,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Date,
    val sport: Sport,
    val views: Int
)
