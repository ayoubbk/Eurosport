package com.bks.eurosporttest.domain.data.network.model

import com.bks.eurosporttest.domain.model.Sport
import com.google.gson.annotations.SerializedName

data class VideoDto (

    @SerializedName("id")
    var id: Long,

    @SerializedName("title")
    var title: String,

    @SerializedName("thumb")
    var thumb: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("date")
    var longDate: Long,

    @SerializedName("sport")
    var sport: Sport,

    @SerializedName("views")
    var views: Int
)