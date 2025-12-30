package com.metalonomicon.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.metalonomicon.ui.screens.*
import com.metalonomicon.viewmodels.*

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Genres : BottomNavItem(Screen.Genres.route, Icons.Default.MenuBook, "Grimoire")
    object Search : BottomNavItem(Screen.Search.route, Icons.Default.Search, "Search")
    object Analytics : BottomNavItem(Screen.Analytics.route, Icons.Default.Analytics, "Analytics")
}

@Composable
fun MetalonomiconApp(
    genresViewModel: GenresViewModel,
    genreDetailViewModel: GenreDetailViewModel,
    bandDetailViewModel: BandDetailViewModel,
    searchViewModel: SearchViewModel,
    analyticsViewModel: AnalyticsViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem.Genres,
        BottomNavItem.Search,
        BottomNavItem.Analytics
    )

    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Genres.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Genres.route) {
                GenresScreen(
                    viewModel = genresViewModel,
                    onGenreClick = { genreId ->
                        navController.navigate(Screen.GenreDetail.createRoute(genreId))
                    }
                )
            }

            composable(
                route = Screen.GenreDetail.route,
                arguments = listOf(navArgument("genreId") { type = NavType.LongType })
            ) { backStackEntry ->
                val genreId = backStackEntry.arguments?.getLong("genreId") ?: return@composable
                GenreDetailScreen(
                    genreId = genreId,
                    viewModel = genreDetailViewModel,
                    onBackClick = { navController.popBackStack() },
                    onBandClick = { bandId ->
                        navController.navigate(Screen.BandDetail.createRoute(bandId))
                    },
                    onSubgenreClick = { subgenreId ->
                        navController.navigate(Screen.GenreDetail.createRoute(subgenreId))
                    }
                )
            }

            composable(
                route = Screen.BandDetail.route,
                arguments = listOf(navArgument("bandId") { type = NavType.LongType })
            ) { backStackEntry ->
                val bandId = backStackEntry.arguments?.getLong("bandId") ?: return@composable
                BandDetailScreen(
                    bandId = bandId,
                    viewModel = bandDetailViewModel,
                    onBackClick = { navController.popBackStack() },
                    onGenreClick = { genreId ->
                        navController.navigate(Screen.GenreDetail.createRoute(genreId))
                    }
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onBandClick = { bandId ->
                        navController.navigate(Screen.BandDetail.createRoute(bandId))
                    },
                    onGenreClick = { genreId ->
                        navController.navigate(Screen.GenreDetail.createRoute(genreId))
                    }
                )
            }

            composable(Screen.Analytics.route) {
                AnalyticsScreen(viewModel = analyticsViewModel)
            }
        }
    }
}
