package com.mx.cano.tvmaze.connection.api

import com.mx.cano.tvmaze.connection.data.ScheduleResponse
import retrofit2.Response
import retrofit2.http.*

interface TVMazeApi {
    @Headers("Content-Type: application/json")
    @GET("schedule")
    suspend fun schedule(
        @Query("country") country: String = "US",
        @Query("date") date: String,
    ): Response<List<ScheduleResponse>>
}