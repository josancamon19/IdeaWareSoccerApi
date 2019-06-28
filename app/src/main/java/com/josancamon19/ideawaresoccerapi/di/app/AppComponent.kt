package com.josancamon19.ideawaresoccerapi.di.app

import android.app.Application
import com.josancamon19.ideawaresoccerapi.BaseApplication
import com.josancamon19.ideawaresoccerapi.di.viewmodel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesBuilderModule::class,
        AppModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication>{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}