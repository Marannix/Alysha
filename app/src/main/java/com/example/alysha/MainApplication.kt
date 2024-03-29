package com.example.alysha

import android.app.Activity
import android.app.Application
import com.example.alysha.dagger.component.ApplicationComponent
import com.example.alysha.dagger.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initComponent().inject(this)
    }

    private fun initComponent() : ApplicationComponent {
        if (!this::applicationComponent.isInitialized) {
            applicationComponent = createComponent()
        }
        return applicationComponent
    }

    private fun createComponent() : ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}