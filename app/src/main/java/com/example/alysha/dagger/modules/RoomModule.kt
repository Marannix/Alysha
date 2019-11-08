package com.example.alysha.dagger.modules

import android.app.Application
import androidx.room.Room
import com.example.alysha.data.CountryDao
import com.example.alysha.database.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): ApplicationDatabase {
        return Room.databaseBuilder(
            application,
            ApplicationDatabase::class.java, "alysha.db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providesCountryDao(applicationDatabase: ApplicationDatabase): CountryDao {
        return applicationDatabase.countryDao()
    }
}