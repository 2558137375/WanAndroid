package com.example.test.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.test.R
import com.example.test.logic.network.model.DetailIntentList
import com.example.test.logic.network.model.DetailTabIntentData
import com.example.test.ui.adapter.Pager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EditDetailActivity : AppCompatActivity() {
    companion object {
        const val INTENT_TABS_LIST = "INTENT_TABS_LIST"
        const val INTENT_BUNDLE = "INTENT_BUNDLE"
    }

    private lateinit var detailViewPager2: ViewPager2
    private lateinit var detailTabLayout: TabLayout
    private lateinit var editDetailBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_detail)
        // 初始化视图
        detailViewPager2 = findViewById(R.id.detailViewPager2)
        detailTabLayout = findViewById(R.id.detailTabLayout)
        editDetailBack = findViewById(R.id.editDetailBack)

        // 设置返回按钮的点击事件
        editDetailBack.setOnClickListener {
            finish()
        }

        // 获取intent数据
        var intentList: DetailIntentList? = null
        val bundle: Bundle? = intent.extras
        bundle?.let {
            intentList = it.getParcelable(INTENT_TABS_LIST)
            Log.d("EditDetailActivity", "Received Intent List: $intentList")
            Log.d("EditDetailActivity", "Received List Data: ${intentList?.list}")
        }

        // 初始化ViewPager2
        initPageModule(intentList?.list ?: emptyList())

        // 绑定TabLayout和ViewPager2
        TabLayoutMediator(detailTabLayout, detailViewPager2, true, true) { tab, position ->
            val tabView = layoutInflater.inflate(R.layout.tab_item, null)
            val tabItemTitle: TextView = tabView.findViewById(R.id.tabItemTitle)
            tabItemTitle.text = intentList?.list?.get(position)?.name
            tab.customView = tabView
        }.attach()
    }

    private fun initPageModule(list: List<DetailTabIntentData>) {
        val pageFragList = mutableListOf<Fragment>()
        list.forEach { data ->
            Log.d("EditDetailActivity", "Tab Data - Name: ${data.name}, ID: ${data.id}")
            pageFragList.add(EditDetailListFragment(name = data.name ?: "", cid = data.id))
        }
        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(pageFragList)

        // 默认不做预加载Fragment
        detailViewPager2.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        detailViewPager2.adapter = pageAdapter
    }
}