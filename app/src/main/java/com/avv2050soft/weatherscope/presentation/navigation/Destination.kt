package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object SavedLocations: Destination {
    override val icon: ImageVector
        get() = Icons.TwoTone.LocationOn
    override val route: String
        get() = "saved_locations_screen"
}
object AutocompleteLocations: Destination{
    override val icon: ImageVector
        get() = Icons.TwoTone.Edit
    override val route: String
        get() = "autocomplete_location_screen"

}