package com.example.test.logic.network

import com.example.test.logic.network.model.EditDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EditDetailService {

    @GET("/article/list/{pageCount}/json")
    fun getEditDetail(
        @Path("pageCount") pageCount: String = "0",
        @Query("cid") cid: String
    ): Call<EditDetailResponse>
}