package com.example.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.logic.Repository
import com.example.test.logic.network.model.ProjectDetailResponse
import kotlinx.coroutines.launch

class ProjectDetailViewModel : ViewModel() {
    private val _projectDetailResponse = MutableLiveData<ProjectDetailResponse>()
    val projectDetailResponse: LiveData<ProjectDetailResponse> get() = _projectDetailResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchProjectDetail(pageCount: String = "1", cid: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getProjectDetail(pageCount, cid)
                _projectDetailResponse.value = response
            } catch (e: Exception) {
                _error.value = "Error fetching data: ${e.message}"
            }
        }
    }
}