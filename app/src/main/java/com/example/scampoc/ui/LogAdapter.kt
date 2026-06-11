package com.example.scampoc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scampoc.data.CapturedItem

class LogAdapter : ListAdapter<CapturedItem, LogAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)

        fun bind(item: CapturedItem) {
            textView.text = itemView.context.getString(
                com.example.scampoc.R.string.log_format,
                item.source,
                item.text
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CapturedItem>() {
        override fun areItemsTheSame(oldItem: CapturedItem, newItem: CapturedItem): Boolean = 
            oldItem.timestamp == newItem.timestamp
        override fun areContentsTheSame(oldItem: CapturedItem, newItem: CapturedItem): Boolean = 
            oldItem == newItem
    }
}
