package com.example.test.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.logic.Repository
import com.example.test.logic.network.model.EditItem
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    private val _editList = MutableLiveData<List<EditItem>>()
    val editList: LiveData<List<EditItem>> get() = _editList

    fun fetchEdit() {
        viewModelScope.launch {
            try {
                val editItems = Repository.getEdit()
                _editList.value = editItems
            } catch (e: Exception) {
                Log.e("EditViewModel", "Error fetching edit items", e)
                _editList.value = emptyList()
            }
        }
    }
}
