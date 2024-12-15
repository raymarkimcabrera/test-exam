package com.example.randomuserapp.data.remote

import com.example.randomuserapp.data.model.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteInterface {
    @GET("api/")
    suspend fun getRandomUsers(
        @Query("results") count: Int = 50,
    ): Response<RandomUserResponse>
}
