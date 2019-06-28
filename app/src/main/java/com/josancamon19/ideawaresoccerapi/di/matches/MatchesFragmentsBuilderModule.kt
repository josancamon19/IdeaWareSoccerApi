package com.josancamon19.ideawaresoccerapi.di.matches

import com.josancamon19.ideawaresoccerapi.ui.matches.FixturesFragment
import com.josancamon19.ideawaresoccerapi.ui.main.MainFragment
import com.josancamon19.ideawaresoccerapi.ui.matches.ResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MatchesFragmentsBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFixturesFragment(): FixturesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeResultsFragment(): ResultsFragment
}