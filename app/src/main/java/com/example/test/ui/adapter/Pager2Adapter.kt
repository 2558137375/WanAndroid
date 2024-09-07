package com.example.test.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter


class Pager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val itemFragList = mutableListOf<Fragment>()

    override fun getItemCount() = itemFragList.size

    override fun createFragment(position: Int) = itemFragList[position]

    fun setData(newListData: List<Fragment>) {
        //使用DiffUtil更新数据
        val callback = PageDiffUtil(itemFragList, newListData)
        val difResult = DiffUtil.calculateDiff(callback)
        itemFragList.clear()
        itemFragList.addAll(newListData)
        difResult.dispatchUpdatesTo(this)
    }

}
