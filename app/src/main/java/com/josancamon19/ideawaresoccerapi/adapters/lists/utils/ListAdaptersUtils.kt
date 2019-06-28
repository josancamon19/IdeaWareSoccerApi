package com.josancamon19.ideawaresoccerapi.adapters.lists.utils

import com.josancamon19.ideawaresoccerapi.models.DataItem
import com.josancamon19.ideawaresoccerapi.models.Match
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.parse
import timber.log.Timber

fun addHeadersToList(list: List<Match>?) : List<DataItem>{
    Timber.d(list?.size.toString())
    val listWithHeaders = arrayListOf<DataItem>()
    if(list?.size!! > 0){
        val firstMatchDate = getDate(list[0].date)
        listWithHeaders.add(DataItem.Header("${firstMatchDate.month.name} ${firstMatchDate.year.year}"))
        if(list.size == 1){
            listWithHeaders.add(DataItem.MatchItem(list[0]))
            return listWithHeaders
        }
        for (i in 1 until list.size) {

            val prevMatch = list[i - 1]
            val nextMatch = list[i]

            val prevMatchDate = getDate(prevMatch.date)
            val nextMatchDate = getDate(nextMatch.date)

            listWithHeaders.add(DataItem.MatchItem(prevMatch))

            if (prevMatchDate.month0 != nextMatchDate.month0) {
                //Timber.d("Prev date %s : Next Date %s", prevMatchDate.toString(), nextMatchDate.toString())
                listWithHeaders.add(DataItem.Header("${nextMatchDate.month.name} ${nextMatchDate.year.year}"))
            }
            if(i == list.size-1){
                listWithHeaders.add(DataItem.MatchItem(nextMatch))
            }
        }
    }
    return listWithHeaders
}

fun getDate(dt: String): DateTimeTz {
    val dateSubString = dt.substring(0, dt.length - 5)
    val dateFormat = DateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return dateFormat.parse(dateSubString)
}