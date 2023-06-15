package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ContentAdapter(private val items: List<Int?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(position, items[position])
        }
    }

    fun headerStickFrom() = 10
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(position: Int, item: Int?) {
        itemView.findViewById<View>(R.id.header).isVisible = position == 10
        itemView.findViewById<TextView>(R.id.tvContent).text = item.toString()
    }
}