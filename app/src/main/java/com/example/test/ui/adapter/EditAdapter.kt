package com.example.test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.logic.network.model.EditItem


class EditAdapter(private var editList: List<EditItem>,
                  private val onItemClick: (EditItem) -> Unit) :
    RecyclerView.Adapter<EditAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemEditTitle:TextView = view.findViewById(R.id.itemEditTitle)
        val itemEditSubTitle:TextView = view.findViewById(R.id.itemEditSubTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.edit_item,
            parent, false
        )
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val edit = editList[position]
        holder.itemEditTitle.text = edit.name
        val sb = StringBuilder()

        edit.children?.forEach { child ->
            child?.name?.let {
                sb.append(it)
                sb.append("  ")
            }
        }

        holder.itemEditSubTitle.text = sb.toString()
        holder.itemView.setOnClickListener {
            onItemClick(edit)
        }

    }
    override fun getItemCount() = editList?.size?:0
    fun updateData(newList: List<EditItem>) {
        editList = newList
        notifyDataSetChanged()
    }
}