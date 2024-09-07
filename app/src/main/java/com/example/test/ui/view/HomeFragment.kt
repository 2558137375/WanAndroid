package com.example.test.ui.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test.R
import com.example.test.ui.adapter.HomeAdapter
import com.example.test.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter(this,viewModel.homeList)
        recyclerView.adapter = homeAdapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

//        viewModel.homeLiveData.observe(viewLifecycleOwner, Observer { result ->
//            swipeRefreshLayout.isRefreshing = false
//            result.onSuccess { result ->
//                val home = result.data.datas
//                Log.e(TAG, "onActivityCreated: $home", )
//                home?.let { viewModel.homeList.addAll(it) }
//                homeAdapter.notifyDataSetChanged()
//            }
//        })

        viewModel.homeLiveData.observe(viewLifecycleOwner, Observer { result ->
            swipeRefreshLayout.isRefreshing = false
            result.onSuccess { data ->
                Log.d(TAG, "Data loaded successfully: ${data.data.datas?.size} items")
//                if (viewModel.currentPage.value == 1) {
//                    viewModel.homeList.clear() // 清空列表以便显示新的数据
//                }
                data.data.datas?.let { viewModel.homeList.addAll(it) }
                homeAdapter.notifyDataSetChanged()
            }
            result.onFailure {
                Log.e(TAG, "Failed to load data: ${it.message}")
            }
        })

        // 初次加载数据
        viewModel.refresh()

        // 设置RecyclerView的滚动监听以实现分页加载更多数据
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                    Log.d(TAG, "Reached bottom, loading more data...")
                    // 到达底部时加载更多
                    viewModel.loadMore()
                }
            }
        })
    }

//    private fun refreshData() {
//        viewModel.getHome(1) // or any page number if pagination is used
//        val swipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
//        swipeRefreshLayout?.isRefreshing = true
//    }

}
