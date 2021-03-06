package com.bks.eurosporttest.data.network.model

import com.bks.eurosporttest.domain.model.Sport
import com.google.gson.annotations.SerializedName

data class StoryDto(

    @SerializedName("id")
    var id: Long,

    @SerializedName("title")
    var title: String,

    @SerializedName("teaser")
    var teaser: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("date")
    var date: Double,

    @SerializedName("author")
    var author: String,

    @SerializedName("sport")
    var sport: Sport
)