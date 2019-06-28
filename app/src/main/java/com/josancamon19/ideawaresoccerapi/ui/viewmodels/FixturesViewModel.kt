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

class FixturesViewModel @Inject constructor(private val matchesApi: MatchesApi): ViewModel() {
    private val jobViewModel: Job = Job()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Main + jobViewModel)

    private val _fixtures = MutableLiveData<List<Match>>()

    private val _fixturesWithHeaders = MutableLiveData<List<DataItem>>()
    val fixturesWithHeaders: LiveData<List<DataItem>>
        get() = _fixturesWithHeaders

    private val _pbVisibility = MutableLiveData<Boolean>()
    val pbVisibility: LiveData<Boolean>
        get() = _pbVisibility

    init {
        _pbVisibility.value = true
        loadFixtures()
    }

    private fun loadFixtures() {
        uiScope.launch {
            val fixtures = matchesApi.getFixturesAsync()
            try {
                val fixturesList = fixtures.await()
                _fixtures.value = fixturesList
                _fixturesWithHeaders.value =
                    addHeadersToList(fixturesList)
                _pbVisibility.value = false
            } catch (e: Exception) {
                Timber.e(e)
                _pbVisibility.value = false
            }
        }
    }

    fun filterByCompetition(competition:String){
        if (competition == "Display all"){
            _fixturesWithHeaders.value  =
                addHeadersToList(_fixtures.value)
            return
        }
        val matches = mutableListOf<Match>()
        _fixtures.value?.forEach {
            if(it.competitionStage.competition.name == competition){
                matches.add(it)
            }
        }
        _fixturesWithHeaders.value = addHeadersToList(matches)
    }

    override fun onCleared() {
        super.onCleared()
        jobViewModel.cancel()
    }
}