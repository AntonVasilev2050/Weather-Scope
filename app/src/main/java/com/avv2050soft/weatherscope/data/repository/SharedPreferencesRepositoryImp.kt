package com.avv2050soft.weatherscope.data.repository

import android.content.Context
import com.avv2050soft.weatherscope.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

private const val WeatherScopePreferences = "weather_scope_preferences"

class SharedPreferencesRepositoryImp @Inject constructor(
    context: Context
) : SharedPreferencesRepository {
    private val sharedPreferences =
        context.getSharedPreferences(WeatherScopePreferences, Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }
}