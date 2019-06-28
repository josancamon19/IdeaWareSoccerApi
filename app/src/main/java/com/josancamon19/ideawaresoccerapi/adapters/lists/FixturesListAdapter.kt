package com.josancamon19.ideawaresoccerapi.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.ideawaresoccerapi.R
import com.soywiz.klock.DateFormat
import com.josancamon19.ideawaresoccerapi.adapters.lists.utils.DataItemDiffUtil
import com.josancamon19.ideawaresoccerapi.adapters.lists.utils.getDate
import com.josancamon19.ideawaresoccerapi.databinding.ListItemFixtureBinding
import com.josancamon19.ideawaresoccerapi.models.DataItem
import com.josancamon19.ideawaresoccerapi.models.Match
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.TimezoneOffset

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1


class FixturesListAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> MonthViewHolder.from(
                parent
            )
            ITEM_VIEW_TYPE_ITEM -> FixturesViewHolder.from(
                parent
            )
            else -> throw  ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.MatchItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FixturesViewHolder -> {
                val item = getItem(position) as DataItem.MatchItem
                holder.bind(item.match)
            }
            is MonthViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.date)
            }
        }
    }


    class FixturesViewHolder(private val itemBinding: ListItemFixtureBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        companion object {
            fun from(parent: ViewGroup): FixturesViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_fixture, parent, false)
                return FixturesViewHolder(
                    ListItemFixtureBinding.bind(view)
                )
            }
        }

        fun bind(match: Match) {
            itemBinding.match = match
            setDateFields(match.date)
            itemBinding.executePendingBindings()
        }

        private fun setDateFields(dt: String) {
            val date: DateTimeTz = getDate(dt)

            val weekDay = date.dayOfWeek.name
            val monthDay = date.dayOfMonth.toString()

            val dateFormat2 = DateFormat("MMM dd, yyyy at HH:mm")
            val timezoneOffset: TimezoneOffset = date.local.localOffset
            val finalStrDate = dateFormat2.format(date.local.toOffsetUnadjusted(offset = timezoneOffset))

            itemBinding.tvDate.text = finalStrDate
            itemBinding.tvDayOfMonth.text = monthDay
            itemBinding.tvDayOfWeek.text = weekDay.substring(0, 3).toUpperCase()
        }
    }

}


