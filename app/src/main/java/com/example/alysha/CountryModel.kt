package com.example.alysha

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)