package com.akmal.kreasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.akmal.kreasi.databinding.ItemRowLearningBinding

class LearningPathAdapter(private val listLearning: ArrayList<LearningPath>): RecyclerView.Adapter<LearningPathAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(private val binding: ItemRowLearningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(learningPath: LearningPath) {
            binding.tvItemName.text = learningPath.name
            binding.tvItemDescription.text = learningPath.description
            binding.imgItemPhoto.setImageResource(learningPath.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowLearningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listLearning.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val learningPath = listLearning[position]
        holder.bind(learningPath)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listLearning[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: LearningPath)
    }
}