package com.example.test.logic.network

import com.example.test.logic.network.model.ProjectResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProjectService {
    @GET("/project/tree/json")
    fun getProject(): Call<ProjectResponse>
}