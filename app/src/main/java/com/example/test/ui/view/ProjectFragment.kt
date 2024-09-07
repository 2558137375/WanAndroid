package com.example.test.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.test.R
import com.example.test.logic.network.model.DetailIntentList
import com.example.test.logic.network.model.DetailTabIntentData
import com.example.test.ui.adapter.Pager2Adapter
import com.example.test.ui.adapter.Pager2Adapter1
import com.example.test.ui.view.EditDetailActivity.Companion.INTENT_TABS_LIST
import com.example.test.ui.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProjectFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewModel: ProjectViewModel
    private lateinit var pager2Adapter1: Pager2Adapter1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.projectTabLayout)
        viewPager2 = view.findViewById(R.id.projectViewPager2)

        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        viewModel.projectList.observe(viewLifecycleOwner){ projectList ->
            val tabData = projectList.map { project ->
                Pair(project.id.toString(), "1")
            }

            pager2Adapter1 = Pager2Adapter1(requireActivity(), tabData)
            viewPager2.adapter = pager2Adapter1

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = projectList[position].name
            }.attach()
        }

        // Fetch data when the fragment is created
        viewModel.fetchProject()
    }
}
