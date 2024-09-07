package com.example.test.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.bumptech.glide.Glide
import com.example.test.R
import com.example.test.logic.network.model.DataX
import com.example.test.ui.TestApplication.Companion.context
import com.example.test.ui.view.WebActivity

class ProjectDetailAdapter : RecyclerView.Adapter<ProjectDetailAdapter.ProjectDetailViewHolder>() {

    private var projectDetailList: List<DataX> = listOf()

    // ViewHolder 用于绑定视图
    class ProjectDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val onTop: TextView = view.findViewById(R.id.on_top)
        val fresh: TextView = view.findViewById(R.id.fresh)
        val date: TextView = view.findViewById(R.id.date)
        val title: TextView = view.findViewById(R.id.title)
        val desc: TextView = view.findViewById(R.id.desc)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val chapter: TextView = view.findViewById(R.id.chapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectDetailViewHolder {
        // 使用 layoutInflater 来加载布局
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_item, parent, false)
        return ProjectDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectDetailViewHolder, position: Int) {
        // 获取当前位置的项目
        val projectDetail = projectDetailList[position]

        // 绑定数据到视图
        holder.author.text = projectDetail.author
        holder.onTop.visibility = if (projectDetail.isAdminAdd) View.VISIBLE else View.GONE
        holder.fresh.visibility = if (projectDetail.fresh) View.VISIBLE else View.GONE
        holder.date.text = projectDetail.niceDate
        holder.title.text = projectDetail.title
        holder.desc.text = projectDetail.desc
        holder.chapter.text = projectDetail.chapterName

        // 加载缩略图
        // 可以使用图片加载库，例如 Glide 或 Picasso
        Glide.with(holder.thumbnail.context)
            .load(projectDetail.envelopePic)
            .placeholder(R.drawable.project_24px) // 占位符
            .into(holder.thumbnail)

        // 处理点击事件（可选）
        holder.itemView.setOnClickListener {
            // 在这里处理每项的点击事件
            // 可以通过接口或 Lambda 将点击事件传递到 Fragment 或 Activity
            val intent = Intent(it.context, WebActivity::class.java)
            intent.putExtra(WebActivity.intentKeyTitle, projectDetail.title)
            intent.putExtra(WebActivity.intentKeyUrl, projectDetail.link)
            startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return projectDetailList.size
    }

    fun updateData(newList: List<DataX>) {
        projectDetailList = newList
        notifyDataSetChanged()
    }
}
