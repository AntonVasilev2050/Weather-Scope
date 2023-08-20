package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class GetLocationFromPreferencesUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun getString(key: String, defaultValue: String): String? {
        return repository.getString(key, defaultValue)
    }
}