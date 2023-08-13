package com.avv2050soft.weatherscope.domain.repository

interface SharedPreferencesRepository {
    fun saveString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String?
}