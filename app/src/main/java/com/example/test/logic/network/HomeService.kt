package com.example.test.logic.network

import com.example.test.logic.network.model.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("/article/list/{page}/json")
    fun getHome(
        @Path("page") page: Int,
    ): Call<HomeResponse>
}