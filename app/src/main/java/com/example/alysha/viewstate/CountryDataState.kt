package com.example.alysha.viewstate

import com.example.alysha.CountryModel

sealed class CountryDataState {
    data class Success(val countryModel: List<CountryModel>) : CountryDataState()
    data class Error(val errorMessage: String?) : CountryDataState()
}