package com.josancamon19.ideawaresoccerapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainActivityViewModel : ViewModel() {

    private val _filterOptions = MutableLiveData<List<String>>()
    val filterOptions: LiveData<List<String>>
        get() = _filterOptions

    private val _fabVisibility = MutableLiveData<Boolean>()
    val fabVisibility: LiveData<Boolean>
        get() = _fabVisibility


    private val _competitionSelected = MutableLiveData<String>()
    val competitionSelected: LiveData<String>
        get() = _competitionSelected


    fun setFilterOptions(options: List<String>) {
        Timber.d(options.toString())
        _filterOptions.value = options
        if (options.isNotEmpty()){
            _fabVisibility.value = true
        }
    }

    fun setCompetition(competition: String) {
        _competitionSelected.value = competition
    }
}