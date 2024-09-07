package com.example.test.logic.network

import com.example.test.logic.network.model.EditResponse
import retrofit2.Call
import retrofit2.http.GET

interface EditService {
    @GET("/tree/json")
    fun getEdit(): Call<EditResponse>
}