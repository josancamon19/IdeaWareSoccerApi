package com.josancamon19.ideawaresoccerapi.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.ideawaresoccerapi.R
import com.josancamon19.ideawaresoccerapi.databinding.ListItemMonthBinding


class MonthViewHolder(private val itemBinding: ListItemMonthBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun from(parent: ViewGroup): MonthViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_month, parent, false)
            return MonthViewHolder(ListItemMonthBinding.bind(view))
        }
    }

    fun bind(date: String) {
        itemBinding.dateSeparator.text = date
        itemBinding.executePendingBindings()
    }


}