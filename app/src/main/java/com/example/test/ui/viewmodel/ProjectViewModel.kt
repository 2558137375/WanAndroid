package com.example.test.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.logic.Repository
import com.example.test.logic.network.model.EditItem
import com.example.test.logic.network.model.ProjectData
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {
    private val _projectList = MutableLiveData<List<ProjectData>>()
    val projectList: LiveData<List<ProjectData>> get() = _projectList

    fun fetchProject() {
        viewModelScope.launch {
            try {
                val ProjectData = Repository.getProject()
                _projectList.value = ProjectData
                Log.e("ProjectViewModel", "$_projectList")
            } catch (e: Exception) {
                Log.e("ProjectViewModel", "Error fetching edit items", e)
                _projectList.value = emptyList()
            }
        }
    }
}