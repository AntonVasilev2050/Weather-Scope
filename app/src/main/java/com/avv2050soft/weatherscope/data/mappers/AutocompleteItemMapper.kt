package com.avv2050soft.weatherscope.data.mappers

import com.avv2050soft.weatherscope.data.local.dto.LocationItemDto
import com.avv2050soft.weatherscope.domain.models.autocomplete.AutocompleteItem

class AutocompleteItemMapper {
    fun mapAutocompleteItemToLocationItemDto(autocompleteItem: AutocompleteItem) = LocationItemDto(
        country = autocompleteItem.country,
        id = autocompleteItem.id,
        lat = autocompleteItem.lat,
        lon = autocompleteItem.lon,
        name = autocompleteItem.name,
        region = autocompleteItem.region,
        url = autocompleteItem.url
    )
}