package com.example.test.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test.R
import com.example.test.ui.adapter.ProjectDetailAdapter
import com.example.test.ui.viewmodel.ProjectDetailViewModel

class ProjectDetailFragment : Fragment() {

    private lateinit var viewModel: ProjectDetailViewModel
    private var cid: String? = null
    private var pageCount: String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cid = it.getString("cid")
            pageCount = it.getString("pageCount", "1")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        val adapter = ProjectDetailAdapter()

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.setOnRefreshListener {
            cid?.let { c ->
                viewModel.fetchProjectDetail(pageCount, c)
            }
        }
        // 初始化 viewModel
        viewModel = ViewModelProvider(this).get(ProjectDetailViewModel::class.java)
        viewModel.projectDetailResponse.observe(viewLifecycleOwner) { response ->
            response.data.datas?.let {
                Log.e("ProjectDetailFragment", "onViewCreated:${response} ",)
                adapter.updateData(it) // Update the list in adapter
            }
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner){ errorMsg ->
            // Handle error message, e.g., show a Toast
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        }

        cid?.let { c ->
            viewModel.fetchProjectDetail(pageCount, c)
        }

    }

    companion object {
        fun newInstance(cid: String, pageCount: String = "1"): ProjectDetailFragment {
            val fragment = ProjectDetailFragment()
            val args = Bundle().apply {
                putString("cid", cid)
                putString("pageCount", pageCount)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
