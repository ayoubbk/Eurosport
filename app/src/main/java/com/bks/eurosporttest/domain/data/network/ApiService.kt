package com.bks.eurosporttest.domain.data.network

import com.bks.eurosporttest.domain.data.network.response.NetworkResponse
import retrofit2.http.GET

interface ApiService {

    @GET("edfefba")
    suspend fun get(): NetworkResponse
}