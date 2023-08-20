package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SaveLocationToPreferencesUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun saveString(key: String, value:String){
        repository.saveString(key,value)
    }
}