package com.example.test.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.ui.view.ProjectDetailFragment

class Pager2Adapter1(fa: FragmentActivity, private val tabData: List<Pair<String, String>>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = tabData.size

    override fun createFragment(position: Int): Fragment {
        val (cid, pageCount) = tabData[position]
        return ProjectDetailFragment.newInstance(cid, pageCount)
    }
}
