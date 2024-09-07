package com.example.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.test.logic.Repository
import com.example.test.logic.network.model.Data
import com.example.test.logic.network.model.Datas
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel(){
    val homeList = ArrayList<Datas>()

    private val _currentPage = MutableLiveData<Int>().apply { value = 0 }
    val currentPage: LiveData<Int> get() = _currentPage
    val homeLiveData = currentPage.switchMap() { page ->
        Repository.getHome(page)
    }
    // 更新当前页数
    fun updatePage(page: Int) {
        _currentPage.value = page
    }

    // 增加当前页数并加载更多数据
    fun loadMore() {
        _currentPage.value?.let {
            updatePage(it + 1)
        }
    }

    // 刷新数据时重置页数
    fun refresh() {
        updatePage(0)
    }
}