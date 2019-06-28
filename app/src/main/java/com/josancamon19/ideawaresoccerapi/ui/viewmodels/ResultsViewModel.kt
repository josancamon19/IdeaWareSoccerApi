package com.josancamon19.ideawaresoccerapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.josancamon19.ideawaresoccerapi.adapters.lists.utils.addHeadersToList
import com.josancamon19.ideawaresoccerapi.models.DataItem
import com.josancamon19.ideawaresoccerapi.models.Match
import com.josancamon19.ideawaresoccerapi.network.MatchesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ResultsViewModel @Inject constructor(private val matchesApi: MatchesApi): ViewModel() {
    private val jobViewModel: Job = Job()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Main + jobViewModel)

    private val _results = MutableLiveData<List<Match>>()

    private val _resultsWithHeaders = MutableLiveData<List<DataItem>>()
    val resultsWithHeaders: LiveData<List<DataItem>>
        get() = _resultsWithHeaders

    private val _competitions = MutableLiveData<List<String>>()
    val competitions: LiveData<List<String>>
        get() = _competitions

    private val _pbVisibility = MutableLiveData<Boolean>()
    val pbVisibility: LiveData<Boolean>
        get() = _pbVisibility

    init {
        _pbVisibility.value = true
        loadFixtures()
    }

    private fun loadFixtures() {
        uiScope.launch {
            val fixtures = matchesApi.getResultsAsync()
            try {
                val fixturesList = fixtures.await()
                _results.value = fixturesList
                _resultsWithHeaders.value =
                    addHeadersToList(fixturesList)
                loadCompetitions()
                _pbVisibility.value = false
            } catch (e: Exception) {
                Timber.e(e)
                _pbVisibility.value = false
            }
        }
    }

    private fun loadCompetitions() {
        val competitions = mutableSetOf<String>()
        _results.value?.forEach {
            competitions.add(it.competitionStage.competition.name)
        }
        _competitions.value = competitions.toList()
    }

    fun filterByCompetition(competition:String){
        if (competition == "Display all"){
            _resultsWithHeaders.value  =
                addHeadersToList(_results.value)
            return
        }
        val matches = mutableListOf<Match>()
        _results.value?.forEach {
            if(it.competitionStage.competition.name == competition){
                matches.add(it)
            }
        }
        _resultsWithHeaders.value = addHeadersToList(matches)
    }


    override fun onCleared() {
        super.onCleared()
        jobViewModel.cancel()
    }
}