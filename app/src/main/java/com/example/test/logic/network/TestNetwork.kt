package com.example.test.logic.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.test.logic.network.model.EditDetailResponse
import com.example.test.logic.network.model.EditResponse
import com.example.test.logic.network.model.HomeResponse
import com.example.test.logic.network.model.ProjectDetailResponse
import com.example.test.logic.network.model.ProjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object TestNetwork {
    private val HomeService = ServiceCreator.create<HomeService>()
    suspend fun getHome(page: Int): HomeResponse {
        Log.d(TAG, "Requesting page: $page")
        return HomeService.getHome(page).await()
    }
    private val EditService = ServiceCreator.create<EditService>()
    suspend fun getEdit(): EditResponse {
        return EditService.getEdit().await()
    }
    private val EditDetailService = ServiceCreator.create<EditDetailService>()
    suspend fun getEditDetail(pageCount:String="0",cid: String): EditDetailResponse {
        Log.d("TestNetwork", "Requesting cid: $cid")
        return EditDetailService.getEditDetail(pageCount,cid).await()
    }

    private val ProjectService = ServiceCreator.create<ProjectService>()
    suspend fun getProject(): ProjectResponse {
        return ProjectService.getProject().await()
    }

    private val ProjectDetailService = ServiceCreator.create<ProjectDetailService>()
    suspend fun getProjectDetail(pageCount:String="1",cid: String): ProjectDetailResponse {
        Log.d("TestNetwork", "Requesting cid: $cid")
        return ProjectDetailService.getProjectDetail(pageCount,cid).await()
    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e(TAG, "Request failed: ${t.message}")
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}