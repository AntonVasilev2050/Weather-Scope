package com.avv2050soft.weatherscope.di

import android.app.Application
import android.content.Context
import com.avv2050soft.weatherscope.data.mappers.AutocompleteItemMapper
import com.avv2050soft.weatherscope.data.repository.DatabaseRepositoryImpl
import com.avv2050soft.weatherscope.data.repository.SharedPreferencesRepositoryImp
import com.avv2050soft.weatherscope.data.repository.WeatherRepositoryImpl
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import com.avv2050soft.weatherscope.domain.repository.SharedPreferencesRepository
import com.avv2050soft.weatherscope.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImp(context = context)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(@ApplicationContext context: Context) : DatabaseRepository{
        return DatabaseRepositoryImpl(context = context, mapper = AutocompleteItemMapper())
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAutocompleteItemMapper(): AutocompleteItemMapper{
        return AutocompleteItemMapper()
    }
}