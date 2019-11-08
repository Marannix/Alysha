package com.example.alysha.repository

import com.example.alysha.CountryApi
import com.example.alysha.CountryModel
import com.example.alysha.data.CountryDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val countryApi: CountryApi
) {

    fun getCountriesFromDb(): Single<List<CountryModel>> {
        return countryDao.getCountries()
    }

    fun getCountriesFromApi(): Single<List<CountryModel>> {
        return countryApi.getCountries()
            .doOnSuccess(this::storeCountriesInDb)
            .subscribeOn(Schedulers.io())
    }

    private fun storeCountriesInDb(countries: List<CountryModel>) {
        countryDao.insertCountries(countries)
    }


}