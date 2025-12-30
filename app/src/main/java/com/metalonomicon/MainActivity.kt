package com.metalonomicon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.metalonomicon.data.repository.MetalonomiconRepository
import com.metalonomicon.ui.navigation.MetalonomiconApp
import com.metalonomicon.ui.theme.MetalonomiconTheme
import com.metalonomicon.viewmodels.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val application = application as MetalonomiconApplication
        val database = application.database

        val repository = MetalonomiconRepository(
            genreDao = database.genreDao(),
            bandDao = database.bandDao(),
            albumDao = database.albumDao(),
            crossRefDao = database.bandGenreCrossRefDao(),
            searchDao = database.searchDao()
        )

        val genresViewModel = GenresViewModel(repository)
        val genreDetailViewModel = GenreDetailViewModel(repository)
        val bandDetailViewModel = BandDetailViewModel(repository)
        val searchViewModel = SearchViewModel(repository)
        val analyticsViewModel = AnalyticsViewModel(repository)

        setContent {
            MetalonomiconTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MetalonomiconApp(
                        genresViewModel = genresViewModel,
                        genreDetailViewModel = genreDetailViewModel,
                        bandDetailViewModel = bandDetailViewModel,
                        searchViewModel = searchViewModel,
                        analyticsViewModel = analyticsViewModel
                    )
                }
            }
        }
    }
}
