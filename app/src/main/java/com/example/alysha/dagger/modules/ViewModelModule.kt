package com.example.alysha.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alysha.viewmodel.CountryViewModel
import com.example.alysha.viewmodel.ViewModelFactory
import com.example.alysha.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel::class)
    internal abstract fun bindingCountryViewModel(viewModel: CountryViewModel): ViewModel

}