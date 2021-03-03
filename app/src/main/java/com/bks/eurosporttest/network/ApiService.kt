package com.bks.eurosporttest.network

import com.bks.eurosporttest.network.response.NetworkResponse
import retrofit2.http.GET

interface ApiService {

    @GET("edfefba")
    suspend fun get(): NetworkResponse
}