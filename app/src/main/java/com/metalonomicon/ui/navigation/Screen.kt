package com.metalonomicon.ui.navigation

sealed class Screen(val route: String) {
    object Genres : Screen("genres")
    object GenreDetail : Screen("genre/{genreId}") {
        fun createRoute(genreId: Long) = "genre/$genreId"
    }
    object BandDetail : Screen("band/{bandId}") {
        fun createRoute(bandId: Long) = "band/$bandId"
    }
    object Search : Screen("search")
    object Analytics : Screen("analytics")
}
