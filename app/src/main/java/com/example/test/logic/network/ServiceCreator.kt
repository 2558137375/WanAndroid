package com.example.test.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//创建一个Retrofit构建器使用接口
object ServiceCreator {
    private const val BASE_URL = "https://wanandroid.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}