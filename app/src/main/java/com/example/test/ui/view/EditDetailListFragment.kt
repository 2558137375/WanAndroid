package com.example.test.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.logic.network.model.EditDetailData
import com.example.test.logic.network.OnItemClickListener
import com.example.test.ui.adapter.EditDetailAdapter
import com.example.test.ui.viewmodel.FragDetailViewModel

class EditDetailListFragment(val name: String, val cid: String) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EditDetailAdapter
    private lateinit var viewModel: FragDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 使用LayoutInflater手动填充布局
        val view = inflater.inflate(R.layout.fragment_edit_detail, container, false)

        // 初始化RecyclerView
        recyclerView = view.findViewById(R.id.fragEditListView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 初始化Adapter
        adapter = EditDetailAdapter()
        recyclerView.adapter = adapter

        // 初始化ViewModel
        viewModel = ViewModelProvider(this)[FragDetailViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("EditDetailListFragment", "onViewCreated called")
        // 获取数据并更新UI
        viewModel.editDetail(name,cid)
        viewModel.list.observe(viewLifecycleOwner) {data->
            Log.d("EditDetailListFragment", "LiveData received: $data")
            data?.let {
                adapter.setDataList(it)
            }        }

        // 设置点击事件监听器
        adapter.setOnItemClickListener(object : OnItemClickListener<EditDetailData> {
            override fun onItemClick(item: EditDetailData?, position: Int) {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.title)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                startActivity(intent)
            }
        })
//        recyclerView.adapter = adapter
    }
}
