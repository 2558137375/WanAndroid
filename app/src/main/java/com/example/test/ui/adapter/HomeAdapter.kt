package com.example.test.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.logic.network.model.Datas
import com.example.test.ui.view.HomeFragment
import com.example.test.ui.view.WebActivity


class HomeAdapter(private val fragment: HomeFragment, private val homeList: List<Datas>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeItemAuthor: TextView = view.findViewById(R.id.homeItemAuthor)
        val homeItemDate: TextView = view.findViewById(R.id.homeItemDate)
        val homeItemTitle: TextView = view.findViewById(R.id.homeItemTitle)
        val homeItemType: TextView = view.findViewById(R.id.homeItemType)
        val homeItemLike: TextView = view.findViewById(R.id.homeItemLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.home_item,
            parent, false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val home = homeList[position]
            var intent = Intent(it.context, WebActivity::class.java)
            intent.putExtra(WebActivity.intentKeyTitle, home.title)
            intent.putExtra(WebActivity.intentKeyUrl, home.link)
            fragment.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val home = homeList[position]
        holder.homeItemTitle.text = home.title
        holder.homeItemType.text = home.chapterName
        holder.homeItemDate.text = home.niceDate
        holder.homeItemAuthor.text = home.author
        holder.homeItemLike.text = null

    }
    override fun getItemCount() = homeList.size
}
