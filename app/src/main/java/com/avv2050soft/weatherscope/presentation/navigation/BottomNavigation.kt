package com.avv2050soft.weatherscope.presentation.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avv2050soft.weatherscope.presentation.ui.theme.LightGrey
import com.avv2050soft.weatherscope.presentation.ui.theme.Yellow401
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    val listItems = listOf(
        BottomItem.Today,
        BottomItem.Tomorrow,
        BottomItem.Forecast,
    )
    Surface(
        shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        border = BorderStroke(1.dp, LightGrey)
    ) {
        NavigationBar(
            modifier = Modifier.height(BottomNavigationHeight),
            containerColor = colorScheme.primary,
            contentColor = Color.LightGray
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            listItems.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.popBackStack()
                        navController.navigateSingleTopTo(item.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = "Icon",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title.uppercase(),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Yellow401,
                        selectedTextColor = Yellow401,
                        unselectedIconColor = colorScheme.outlineVariant,
                        unselectedTextColor = colorScheme.outlineVariant,
                        indicatorColor = colorScheme.primary
                    )
                )
            }
        }
    }
}

private val BottomNavigationHeight = 80.dp