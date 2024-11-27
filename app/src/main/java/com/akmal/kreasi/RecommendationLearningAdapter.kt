package com.akmal.kreasi

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.akmal.kreasi.databinding.ListChildBinding
import com.akmal.kreasi.databinding.ListGroupBinding

class RecommendationLearningAdapter(
    private val context: Context,
    private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getChild(listPosition: Int, expandableListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]!![expandableListPosition]
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getChildId(listPosition: Int, expandableListPosition: Int): Long {
        return expandableListPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ListGroupBinding.inflate(LayoutInflater.from(context), parent, false)
        val listTitle = getGroup(listPosition) as String
        binding.groupTitle.text = listTitle
        binding.groupTitle.setTypeface(null, Typeface.BOLD)
        return binding.root
    }

    override fun getChildView(
        listPosition: Int,
        expandableListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ListChildBinding.inflate(LayoutInflater.from(context), parent, false)
        val expandableListText = getChild(listPosition, expandableListPosition) as String
        binding.childTitle.text = expandableListText
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
