package com.josancamon19.ideawaresoccerapi.models

sealed class DataItem {
    data class MatchItem(val match: Match) : DataItem() {
        override val id = match.id
    }

    data class Header(var date: String): DataItem(){
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int
}
