package com.example.alysha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alysha.repository.CountryRepository
import com.example.alysha.viewstate.CountryDataState
import com.example.alysha.viewstate.CountryViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
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

    // TODO: Should not be directly getting from api
//    private fun getCountriesFromApi() {
//        disposables.add(
//            repository.getCountriesFromApi()
//                .observeOn(AndroidSchedulers.mainThread())
//                //TODO: Fix this lol (It's awful)
//                .doOnSuccess { loadCountriesFromDb() }
//                .doOnError { Log.d("Offline", it.cause.toString()) }
//                .subscribe()
//        )
//    }

    // TODO: Should be in a usecase
    private fun getCountriesDataState(): Observable<CountryDataState> {
        // TODO: Should not be getting from api but from a concat instead
        return repository.getCountriesFromApi()
            .toObservable()
            .map<CountryDataState> { list ->
                CountryDataState.Success(list)
            }
            .onErrorReturn { error -> CountryDataState.Error(error.message) }

    }

    fun getCountries() {
        disposables.add(
            getCountriesDataState()
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