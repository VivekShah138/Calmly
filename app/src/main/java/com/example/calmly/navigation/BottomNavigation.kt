package com.example.calmly.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Spa
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavScreen(
    val screen: Screens,
    val title: String,
    val icon: ImageVector
)

val BOTTOM_NAV_DESTINATION = listOf(
    BottomNavScreen(
        screen = Screens.MeditationScreen,
        title = "Meditation",
        icon = Icons.Filled.Spa
    ),
    BottomNavScreen(
        screen = Screens.SleepScreen,
        title = "Sleep",
        icon = Icons.Filled.NightsStay
    )
)



@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavScreen> = BOTTOM_NAV_DESTINATION,
    containerColor: Color = MaterialTheme.colorScheme.background
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        NavigationBar(
            containerColor = containerColor
        ) {
            items.forEachIndexed { index, bottomNavItem ->
                val ifIncluded = currentRoute?.hierarchy?.any { it.hasRoute(bottomNavItem.screen::class) }

                NavigationBarItem(
                    selected = ifIncluded == true,
                    label = {
                        Text(text = bottomNavItem.title)
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.screen){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
//                        restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = bottomNavItem.title
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

