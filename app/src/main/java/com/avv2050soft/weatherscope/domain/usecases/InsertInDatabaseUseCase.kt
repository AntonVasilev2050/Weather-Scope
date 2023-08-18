package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.data.local.dto.LocationItemDto
import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertInDatabaseUseCase @Inject constructor(
    private val repository: DatabaseRepository
){
    suspend fun insertInDb(locationItemDto: LocationItemDto){
        repository.insertInDb(locationItemDto)
    }
}