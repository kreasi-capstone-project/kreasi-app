package com.akmal.kreasi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.akmal.kreasi.databinding.ListChildBinding
import com.akmal.kreasi.databinding.ListGroupBinding

class RecomendationLearningAdapter (
    private val context: Context,
    private val headerData: List<String>,
    private val childData: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = headerData.size

    override fun getChildrenCount(groupPosition: Int): Int {
        return childData[headerData[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any = headerData[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childData[headerData[groupPosition]]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding: ListGroupBinding = if (convertView == null) {
            ListGroupBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            convertView.tag as ListGroupBinding
        }

        binding.groupTitle.text = getGroup(groupPosition).toString()
        binding.root.tag = binding
        return binding.root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding: ListChildBinding = if (convertView == null) {
            ListChildBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            convertView.tag as ListChildBinding
        }

        binding.childTitle.text = getChild(groupPosition, childPosition).toString()
        binding.root.tag = binding
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}