package com.akmal.kreasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akmal.kreasi.databinding.ItemRowHistoryBinding

class HistoryAdapter(private val listHistory: ArrayList<History>): RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(private val binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.tvItemName.text = history.title
            binding.tvItemDate.text = history.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = listHistory[position]
        holder.bind(history)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHistory[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: History)
    }
}