package com.example.test.logic.network

import com.example.test.logic.network.model.ProjectDetailResponse
import com.example.test.logic.network.model.ProjectResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectDetailService {
    @GET("/project/list/{pageCount}/json")
    fun getProjectDetail(
        @Path("pageCount") pageCount: String = "1",
        @Query("cid") cid: String
    ): Call<ProjectDetailResponse>
}