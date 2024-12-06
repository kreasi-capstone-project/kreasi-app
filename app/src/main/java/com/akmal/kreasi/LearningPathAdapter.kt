package com.akmal.kreasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akmal.kreasi.data.response.Subject
import com.akmal.kreasi.databinding.ItemRowLearningBinding

class LearningPathAdapter(private val listener: OnItemClickListener): androidx.recyclerview.widget.ListAdapter<Subject, LearningPathAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private val binding: ItemRowLearningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(learningPath: Subject) {
            binding.tvItemName.text = learningPath.name
            binding.tvItemDescription.text = learningPath.description

            val imgResource = when (learningPath.name) {
                "Cybersecurity" -> R.drawable.artificial_intelligence
                "Data Analytics" -> R.drawable.python
                else -> R.drawable.atom
            }
            binding.imgItemPhoto.setImageResource(imgResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowLearningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val learningPath = getItem(position)
        holder.bind(learningPath)
        holder.itemView.setOnClickListener {
            listener.onItemClick(learningPath)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Subject>() {
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: Subject)
    }
}