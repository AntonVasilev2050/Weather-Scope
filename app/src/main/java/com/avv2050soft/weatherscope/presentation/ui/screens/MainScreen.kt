package com.avv2050soft.weatherscope.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.avv2050soft.weatherscope.presentation.navigation.Locations
import com.avv2050soft.weatherscope.presentation.navigation.BottomNavigation
import com.avv2050soft.weatherscope.presentation.navigation.WeatherScopeNavHost
import com.avv2050soft.weatherscope.presentation.utils.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    navController: NavHostController
) {
//    val navController = rememberNavController()
    Scaffold(
        topBar = { FindLocationRow(navController = navController) },
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        WeatherScopeNavHost(navHostController = navController, weatherViewModel)
    }
}

@Composable
fun FindLocationRow(
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 24.dp)
            .height(FindLocationHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .clickable { navController.navigateSingleTopTo(Locations.route) }
                .padding(4.dp)
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Icon(
                    modifier = Modifier.wrapContentHeight(),
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 8.dp),
                    text = "Krasnodar",
                    textAlign = TextAlign.Left
                )
            }
        }
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable {  },
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Consider my location button"
        )
    }
}

private val FindLocationHeight = 56.dp

