package com.bks.eurosporttest.data.network

import com.bks.eurosporttest.data.network.response.NetworkResponse
import retrofit2.http.GET

interface ApiService {

    @GET("edfefba")
    suspend fun getVideosAndStories(): NetworkResponse


    companion object {
        const val BASE_API_URL = "https://extendsclass.com/api/json-storage/bin/"
    }
}