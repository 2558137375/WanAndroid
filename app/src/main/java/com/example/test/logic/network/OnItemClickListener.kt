package com.example.test.logic.network

interface OnItemClickListener<T> {
    fun onItemClick(item: T?, position: Int)

}