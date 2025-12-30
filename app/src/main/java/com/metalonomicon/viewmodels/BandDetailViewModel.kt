package com.metalonomicon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metalonomicon.data.models.BandWithAlbums
import com.metalonomicon.data.models.BandWithGenres
import com.metalonomicon.data.repository.MetalonomiconRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BandDetailViewModel(
    private val repository: MetalonomiconRepository
) : ViewModel() {

    private val _bandWithAlbums = MutableStateFlow<BandWithAlbums?>(null)
    val bandWithAlbums: StateFlow<BandWithAlbums?> = _bandWithAlbums.asStateFlow()

    private val _bandWithGenres = MutableStateFlow<BandWithGenres?>(null)
    val bandWithGenres: StateFlow<BandWithGenres?> = _bandWithGenres.asStateFlow()

    fun loadBandDetails(bandId: Long) {
        viewModelScope.launch {
            repository.getBandWithAlbums(bandId).collect { data ->
                _bandWithAlbums.value = data
            }
        }
        viewModelScope.launch {
            repository.getBandWithGenres(bandId).collect { data ->
                _bandWithGenres.value = data
            }
        }
    }
}
