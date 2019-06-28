package com.josancamon19.ideawaresoccerapi.di.app

import com.josancamon19.ideawaresoccerapi.ui.main.MainActivity
import com.josancamon19.ideawaresoccerapi.di.matches.MatchesFragmentsBuilderModule
import com.josancamon19.ideawaresoccerapi.di.matches.MatchesModule
import com.josancamon19.ideawaresoccerapi.di.matches.MatchesScope
import com.josancamon19.ideawaresoccerapi.di.matches.MatchesViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilderModule{

    @MatchesScope
    @ContributesAndroidInjector(
        modules = [
            MatchesModule::class,
            MatchesViewModelsModule::class,
            MatchesFragmentsBuilderModule::class
        ]
    )
    internal abstract fun contributesMainActivity() : MainActivity
}