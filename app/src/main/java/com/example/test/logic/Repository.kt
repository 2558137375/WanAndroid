package com.example.test.logic

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.example.test.logic.network.model.EditDetailResponse
import com.example.test.logic.network.model.EditItem
import com.example.test.logic.network.TestNetwork
import com.example.test.logic.network.model.ProjectData
import com.example.test.logic.network.model.ProjectDetailResponse
import com.example.test.logic.network.model.ProjectResponse
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {
    fun getHome(page: Int) = fire(Dispatchers.IO) {
//        Log.d(TAG, "Starting network request for page $page")
        val homeResponse = TestNetwork.getHome(page)
//        Log.e(TAG, "getHome: $homeResponse", )
        if (homeResponse.errorCode == 0 && homeResponse.errorMsg.isNullOrEmpty()) {
            Result.success(homeResponse)
        } else {
//            Log.e(TAG, "Error in response:1 ${homeResponse.errorMsg}")
            Result.failure(RuntimeException("response errorMsg is ${homeResponse.errorMsg}"))
        }
    }

    suspend fun getEdit():List<EditItem>{
        return try {
            // 调用网络方法获取数据
            val editResponse = TestNetwork.getEdit()

            // 检查返回的 editResponse 是否有效
            if (editResponse.errorCode == 0) {
                editResponse.data
            } else {
                // 处理服务器返回的错误
                Log.e("Repository", "Error in response:2 ${editResponse.errorMsg}")
                emptyList() // 返回一个空列表以表示错误情况
            }
        } catch (e: Exception) {
            // 处理网络请求中的异常
            Log.e("Repository", "Network request failed", e)
            emptyList() // 返回一个空列表以表示错误情况
        }
    }

    suspend fun getEditDetail(pageCount: String = "0", cid: String): EditDetailResponse {
        Log.d("Repository", "Fetching data for cid: $cid")
        try {
            return TestNetwork.getEditDetail(cid=cid) // Ensure this matches the method signature
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching data", e)
            throw e
        }
    }

    suspend fun getProject():List<ProjectData>{
        return try {
            // 调用网络方法获取数据
            val ProjectResponse = TestNetwork.getProject()

            // 检查返回的 editResponse 是否有效
            if (ProjectResponse.errorCode == 0) {
                Log.e("Repository",  "${ProjectResponse.data}")
                ProjectResponse.data
            } else {
                // 处理服务器返回的错误
                Log.e("Repository", "Error in response:4 ${ProjectResponse.errorMsg}")
                emptyList() // 返回一个空列表以表示错误情况
            }
        } catch (e: Exception) {
            // 处理网络请求中的异常
            Log.e(TAG, "Network request failed", e)
            emptyList() // 返回一个空列表以表示错误情况
        }
    }

    suspend fun getProjectDetail(pageCount: String = "0", cid: String): ProjectDetailResponse {
        Log.d("Repository", "Fetching data for cid: $cid")
        try {
            return TestNetwork.getProjectDetail(cid=cid) // Ensure this matches the method signature
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching data", e)
            throw e
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}