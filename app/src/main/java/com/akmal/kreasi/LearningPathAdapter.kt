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
                "Leadership and Management" -> R.drawable.leadership_management
                "Strategy" -> R.drawable.strategy
                "Strategy and Operations" -> R.drawable.strategy_operation
                "Critical Thinking" -> R.drawable.critical_thinking
                "Communication" -> R.drawable.communication
                "Business Analysis" -> R.drawable.bussiness_analysis
                "Data Analysis" -> R.drawable.data_analysis
                "Problem Solving" -> R.drawable.problem_solving
                "Decision Making" -> R.drawable.decision_making
                else -> R.drawable.no_pictures
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
                return oldItem.id == newItem.id
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