package com.avv2050soft.weatherscope.domain.usecases

import com.avv2050soft.weatherscope.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteLocationItemFromDatabaseById @Inject constructor(
    repository: DatabaseRepository
) {
}