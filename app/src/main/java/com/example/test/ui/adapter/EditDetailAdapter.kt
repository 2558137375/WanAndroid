package com.example.test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.logic.network.model.EditDetailData
import com.example.test.logic.network.OnItemClickListener

class EditDetailAdapter : RecyclerView.Adapter<EditDetailAdapter.DetailListViewHolder>() {

    private var dataList: List<EditDetailData>? = null
    private var itemClickListener: OnItemClickListener<EditDetailData>? = null

    class DetailListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.itemEditTitle)
        val subTitleTextView: TextView = itemView.findViewById(R.id.itemEditSubTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListViewHolder {
        // 手动填充布局并创建ViewHolder
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.edit_item_detail, parent, false)
        return DetailListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetailListViewHolder, position: Int) {
        // 根据数据列表更新UI
        val item = dataList?.get(position)
        val name = if (item?.author?.isEmpty() == true) {
            item.shareUser
        } else {
            item?.author
        }
        holder.titleTextView.text = name
        holder.subTitleTextView.text = item?.title
        // 设置点击事件监听器
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    fun setDataList(data: List<EditDetailData>?) {
        this.dataList = data
        notifyDataSetChanged()
    }

    // 设置点击事件监听器
    fun setOnItemClickListener(listener: OnItemClickListener<EditDetailData>) {
        this.itemClickListener = listener
    }
}
