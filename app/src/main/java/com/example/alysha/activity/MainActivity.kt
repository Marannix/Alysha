package com.example.alysha.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.alysha.CountryApi
import com.example.alysha.R
import com.example.alysha.viewmodel.CountryViewModel
import com.example.alysha.viewstate.CountryViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CountryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(CountryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getCountries()

        viewModel.state.observe(this, Observer {viewstate ->
            when (viewstate) {
                CountryViewState.Loading -> Log.d("loading???", "Shouldn't work")
                is CountryViewState.ShowCountries -> {
                    Log.d("success???" , viewstate.countryModel[0].name)
                    testString.text = viewstate.countryModel[0].name
                }
                is CountryViewState.ShowError ->Log.d("fail???" , viewstate.errorMessage.toString())
            }
        })

    }
}
