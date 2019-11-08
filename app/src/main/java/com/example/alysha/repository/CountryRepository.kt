package com.example.alysha.repository

import com.example.alysha.CountryApi
import com.example.alysha.CountryModel
import com.example.alysha.data.CountryDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val countryApi: CountryApi
) {

    /**
     * Delays errors from any of them till all terminate.
     * When the device is offline, no need to emit an error state if countries exists in DB
     */

    fun getCountries(): Observable<List<CountryModel>> {
        return Observable.concatArrayEagerDelayError(
            getCountriesFromDb(),
            getCountriesFromApi().toObservable()
        )
    }

    private fun getCountriesFromDb(): Observable<List<CountryModel>> {
        return countryDao.getCountries()
            .toObservable()
            .flatMap { countries ->
                return@flatMap if (countries.isNotEmpty()) {
                    Observable.just(countries)
                } else {
                    Observable.empty()
                }
            }
    }

    private fun getCountriesFromApi(): Single<List<CountryModel>> {
        return countryApi.getCountries()
            .doOnSuccess(this::storeCountriesInDb)
            .subscribeOn(Schedulers.io())
    }

    private fun storeCountriesInDb(countries: List<CountryModel>) {
        countryDao.insertCountries(countries)
    }

}