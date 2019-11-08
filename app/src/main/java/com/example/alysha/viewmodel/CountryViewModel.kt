package com.example.alysha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alysha.usecase.CountryUseCase
import com.example.alysha.viewstate.CountryDataState
import com.example.alysha.viewstate.CountryViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    private val countryUseCase: CountryUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val state = MutableLiveData<CountryViewState>()

//    val list = MutableLiveData<List<CountryModel>>()

//    fun getCountries(): MutableLiveData<List<CountryModel>> {
//        if (list.value.isNullOrEmpty()) {
//            getCountriesFromApi()
//        }
//        return list
//    }

    fun getCountries() {
        disposables.add(
            countryUseCase.getCountriesDataState()
                .observeOn(AndroidSchedulers.mainThread())
                .map { countryDataState ->
                    return@map when (countryDataState) {
                        is CountryDataState.Success -> {
                            CountryViewState.ShowCountries(countryDataState.countryModel)
                        }
                        is CountryDataState.Error -> {
                            CountryViewState.ShowError(countryDataState.errorMessage)
                        }
                    }
                }
                .subscribe { viewState ->
                    this.state.value = viewState
                }
        )
    }

    // TODO: Should not be getting directly from db. One thing at a time, lets try get from api first
//    private fun loadCountriesFromDb() {
//        disposables.add(
//            repository.getCountriesFromDb()
//                .observeOn(AndroidSchedulers.mainThread())
//                .map {
//                    list.value = it
//                }
//                .subscribe()
//        )
//    }
}