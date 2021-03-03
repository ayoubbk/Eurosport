package com.bks.eurosporttest.data.network.response

import com.bks.eurosporttest.data.network.model.StoryDto
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.data.network.model.VideoDto
import com.google.gson.annotations.SerializedName

data class NetworkResponse(

    @SerializedName("videos")
    val videos: List<VideoDto>,

    @SerializedName("stories")
    val stories: List<StoryDto>
)