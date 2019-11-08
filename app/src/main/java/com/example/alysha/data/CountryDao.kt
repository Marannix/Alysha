package com.example.alysha.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alysha.CountryModel
import io.reactivex.Single

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCountries(countries: List<CountryModel>)

    @Query("select * from countries")
    fun getCountries(): Single<List<CountryModel>>
}