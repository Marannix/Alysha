package com.example.alysha

import io.reactivex.Single
import retrofit2.http.GET

interface CountryApi {

    @GET("rest/v2/all")
    fun getCountries() : Single<List<CountryModel>>

}