package com.example.test.ui.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.logic.Repository
import com.example.test.logic.network.model.EditDetailData
import kotlinx.coroutines.launch
import kotlin.math.log

class FragDetailViewModel : ViewModel() {

    // 使用MutableLiveData来存储数据
    val list = MutableLiveData<List<EditDetailData>?>()
    val pageCount = 0
    fun editDetail(pageCount: String, cid: String) {

        viewModelScope.launch {
            Log.e("FragDetailViewModel", "$cid+111", )
            val data = Repository.getEditDetail(cid = cid)
            val resultList = data.data.datas

            Log.e("FragDetailViewModel", "editDetail: $resultList", )

            list.postValue(resultList)
        }
    }
}
