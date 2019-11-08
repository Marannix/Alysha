package com.example.alysha.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alysha.CountryModel
import com.example.alysha.data.CountryDao

@Database(
    entities = [CountryModel::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}