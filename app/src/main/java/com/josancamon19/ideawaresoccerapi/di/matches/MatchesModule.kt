package com.josancamon19.ideawaresoccerapi.di.matches

import com.josancamon19.ideawaresoccerapi.adapters.lists.FixturesListAdapter
import com.josancamon19.ideawaresoccerapi.adapters.lists.ResultsListAdapter
import com.josancamon19.ideawaresoccerapi.network.MatchesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MatchesModule {

    @MatchesScope
    @Provides
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi {
        val retrofitService: MatchesApi by lazy { retrofit.create(MatchesApi::class.java) }
        return retrofitService
    }

    @MatchesScope
    @Provides
    fun provideFixturesListAdapter(): FixturesListAdapter {
        return FixturesListAdapter()
    }

    @MatchesScope
    @Provides
    fun provideResultsListAdapter(): ResultsListAdapter {
        return ResultsListAdapter()
    }
}