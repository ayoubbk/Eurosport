package com.bks.eurosporttest.domain.model

import java.util.*

interface FeaturedItem {
    val id: Long
    val title: String
    val date: Date
    val sport: Sport
}
