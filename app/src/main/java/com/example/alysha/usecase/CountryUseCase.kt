package com.example.alysha.usecase

import com.example.alysha.repository.CountryRepository
import com.example.alysha.viewstate.CountryDataState
import io.reactivex.Observable
import javax.inject.Inject

class CountryUseCase @Inject constructor(
    private val repository: CountryRepository
) {

    fun getCountriesDataState(): Observable<CountryDataState> {
        // TODO: Should not be getting from api but from a concat instead
        return repository.getCountriesFromApi()
            .toObservable()
            .map<CountryDataState> { list ->
                CountryDataState.Success(list)
            }
            .onErrorReturn { error -> CountryDataState.Error(error.message) }
    }

}