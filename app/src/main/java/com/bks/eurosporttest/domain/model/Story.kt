package com.bks.eurosporttest.domain.model

import java.util.*

data class Story(
    val id: Long,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Date,
    val author: String,
    val sport: Sport
)