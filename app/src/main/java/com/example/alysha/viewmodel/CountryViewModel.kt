package com.example.alysha.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alysha.CountryModel
import com.example.alysha.repository.CountryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountryRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val list = MutableLiveData<List<CountryModel>>()

    fun getCountries(): MutableLiveData<List<CountryModel>> {
        if (list.value.isNullOrEmpty()) {
            getCountriesFromApi()
        }
        return list
    }

    private fun getCountriesFromApi() {
        disposables.add(
            repository.getCountriesFromApi()
                .observeOn(AndroidSchedulers.mainThread())
                //TODO: Fix this lol (It's awful)
                .doOnSuccess { loadCountriesFromDb() }
                .doOnError { Log.d("Offline", it.cause.toString()) }
                .subscribe()
        )
    }

    private fun loadCountriesFromDb() {
        disposables.add(
            repository.getCountriesFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    list.value = it
                }
                .subscribe()
        )
    }
}