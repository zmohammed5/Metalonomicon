package com.metalonomicon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metalonomicon.data.dao.CountryCount
import com.metalonomicon.data.dao.DecadeCount
import com.metalonomicon.data.dao.GenreDistribution
import com.metalonomicon.data.repository.MetalonomiconRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DatabaseStats(
    val totalGenres: Int = 0,
    val totalBands: Int = 0,
    val totalAlbums: Int = 0
)

class AnalyticsViewModel(
    private val repository: MetalonomiconRepository
) : ViewModel() {

    private val _databaseStats = MutableStateFlow(DatabaseStats())
    val databaseStats: StateFlow<DatabaseStats> = _databaseStats.asStateFlow()

    private val _bandsByCountry = MutableStateFlow<List<CountryCount>>(emptyList())
    val bandsByCountry: StateFlow<List<CountryCount>> = _bandsByCountry.asStateFlow()

    private val _albumsByDecade = MutableStateFlow<List<DecadeCount>>(emptyList())
    val albumsByDecade: StateFlow<List<DecadeCount>> = _albumsByDecade.asStateFlow()

    private val _genreDistribution = MutableStateFlow<List<GenreDistribution>>(emptyList())
    val genreDistribution: StateFlow<List<GenreDistribution>> = _genreDistribution.asStateFlow()

    init {
        loadAnalytics()
    }

    private fun loadAnalytics() {
        viewModelScope.launch {
            val totalGenres = repository.getTotalGenreCount()
            val totalBands = repository.getTotalBandCount()
            val totalAlbums = repository.getTotalAlbumCount()
            _databaseStats.value = DatabaseStats(totalGenres, totalBands, totalAlbums)
        }

        viewModelScope.launch {
            _bandsByCountry.value = repository.getBandsByCountry(5)
        }

        viewModelScope.launch {
            _albumsByDecade.value = repository.getAlbumsByDecade()
        }

        viewModelScope.launch {
            _genreDistribution.value = repository.getGenreDistribution()
        }
    }
}
