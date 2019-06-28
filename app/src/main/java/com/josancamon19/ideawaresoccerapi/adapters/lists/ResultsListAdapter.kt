package com.josancamon19.ideawaresoccerapi.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.ideawaresoccerapi.R
import com.josancamon19.ideawaresoccerapi.adapters.lists.utils.DataItemDiffUtil
import com.josancamon19.ideawaresoccerapi.adapters.lists.utils.getDate
import com.josancamon19.ideawaresoccerapi.databinding.ListItemResultBinding
import com.josancamon19.ideawaresoccerapi.models.DataItem
import com.josancamon19.ideawaresoccerapi.models.Match
import com.josancamon19.ideawaresoccerapi.models.match.Score
import com.josancamon19.ideawaresoccerapi.adapters.lists.MonthViewHolder
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.TimezoneOffset

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class ResultsListAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> MonthViewHolder.from(
                parent
            )
            ITEM_VIEW_TYPE_ITEM -> ResultsViewHolder.from(
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
            is ResultsViewHolder -> {
                val item = getItem(position) as DataItem.MatchItem
                holder.bind(item.match)
            }
            is MonthViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.date)
            }
        }
    }


    class ResultsViewHolder(private val itemBinding: ListItemResultBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        companion object {
            fun from(parent: ViewGroup): ResultsViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_result, parent, false)
                return ResultsViewHolder(
                    ListItemResultBinding.bind(view)
                )
            }
        }

        fun bind(match: Match) {
            itemBinding.match = match
            setScoreColors(match.score)
            setDateField(match.date)
            itemBinding.executePendingBindings()
        }

        private fun setScoreColors(score: Score) {
            val winnerColor = ContextCompat.getColor(itemBinding.root.context, R.color.colorAccent)
            val looserColor = ContextCompat.getColor(itemBinding.root.context, R.color.textColor)
            val winner = score.winner
            // If ? is removed it will throw a null pointer exception (Apparently a bug in the language)
            winner?.let {
                when (winner) {
                    "home" -> {
                        itemBinding.tvHomeScore.setTextColor(winnerColor)
                        itemBinding.tvAwayScore.setTextColor(looserColor)
                    }
                    "away" -> {
                        itemBinding.tvAwayScore.setTextColor(winnerColor)
                        itemBinding.tvHomeScore.setTextColor(looserColor)
                    }
                    else -> {
                        itemBinding.tvHomeScore.setTextColor(looserColor)
                        itemBinding.tvAwayScore.setTextColor(looserColor)
                    }
                }
            }
        }

        private fun setDateField(dt: String) {
            val date: DateTimeTz = getDate(dt)

            val dateFormat2 = DateFormat("MMM dd, yyyy at HH:mm")
            val timezoneOffset: TimezoneOffset = date.local.localOffset
            val finalStrDate = dateFormat2.format(date.local.toOffsetUnadjusted(offset = timezoneOffset))

            itemBinding.tvDate.text = finalStrDate
        }
    }


}

