package com.example.alysha.activity

import android.os.Bundle
import android.util.Log
import com.example.alysha.CountryApi
import com.example.alysha.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var countryApi: CountryApi

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposables.add(
            countryApi.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("success", it[1].name)
                }, {
                    Log.d("fail", it.cause.toString())
                })
        )
    }
}
