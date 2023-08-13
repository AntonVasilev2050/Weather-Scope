package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object Locations: Destination {
    override val icon: ImageVector
        get() = Icons.TwoTone.LocationOn
    override val route: String
        get() = "locations_screen"
}