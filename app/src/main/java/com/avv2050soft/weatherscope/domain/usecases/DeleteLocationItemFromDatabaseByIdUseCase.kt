package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteLocationItemFromDatabaseByIdUseCase @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend fun deleteLocationItemFromDbById(itemId: Int){
        repository.deleteLocationItemFromDbById(itemId)
    }
}