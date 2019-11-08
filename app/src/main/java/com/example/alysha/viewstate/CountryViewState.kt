package com.example.alysha.viewstate

import com.example.alysha.CountryModel

sealed class CountryViewState {
    object Loading : CountryViewState() // TODO
    data class ShowCountries(val countryModel: List<CountryModel>) : CountryViewState()
    data class ShowError(val errorMessage: String?) : CountryViewState()
}
