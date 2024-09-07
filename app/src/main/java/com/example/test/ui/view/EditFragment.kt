package com.example.test.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.logic.network.model.DetailIntentList
import com.example.test.logic.network.model.DetailTabIntentData
import com.example.test.logic.network.model.EditItem
import com.example.test.ui.adapter.EditAdapter
import com.example.test.ui.viewmodel.EditViewModel

class EditFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(EditViewModel::class.java) }
    private lateinit var editAdapter: EditAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        observeViewModel()
        viewModel.fetchEdit()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragEditListView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        editAdapter = EditAdapter(emptyList()) { editItem ->
            onItemClicked(editItem)
        }
        recyclerView.adapter = editAdapter
    }

    private fun observeViewModel() {
        viewModel.editList.observe(viewLifecycleOwner) { newList ->
            editAdapter.updateData(newList)
        }
    }

    private fun onItemClicked(editItem: EditItem) {
        val tabList = editItem.children?.map { child ->
            DetailTabIntentData(child?.name.orEmpty(), child?.id.toString())
        } ?: emptyList()

        val intentList = DetailIntentList(tabList)
        val intent = Intent(requireContext(), EditDetailActivity::class.java).apply {
            putExtra(EditDetailActivity.INTENT_TABS_LIST, intentList)
        }
        startActivity(intent)
    }
}
