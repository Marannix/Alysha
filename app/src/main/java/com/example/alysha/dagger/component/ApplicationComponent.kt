package com.example.alysha.dagger.component

import android.app.Application
import com.example.alysha.MainApplication
import com.example.alysha.dagger.modules.ActivityBuilder
import com.example.alysha.dagger.modules.ApiModule
import com.example.alysha.dagger.modules.RoomModule
import com.example.alysha.dagger.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ActivityBuilder::class,
        ApiModule::class,
        RoomModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: MainApplication)
}