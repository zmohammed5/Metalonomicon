package com.metalonomicon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metalonomicon.data.entities.Genre
import com.metalonomicon.data.models.GenreWithBands
import com.metalonomicon.data.repository.MetalonomiconRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenreDetailViewModel(
    private val repository: MetalonomiconRepository
) : ViewModel() {

    private val _genreWithBands = MutableStateFlow<GenreWithBands?>(null)
    val genreWithBands: StateFlow<GenreWithBands?> = _genreWithBands.asStateFlow()

    private val _subgenres = MutableStateFlow<List<Genre>>(emptyList())
    val subgenres: StateFlow<List<Genre>> = _subgenres.asStateFlow()

    fun loadGenreDetails(genreId: Long) {
        viewModelScope.launch {
            repository.getGenreWithBands(genreId).collect { data ->
                _genreWithBands.value = data
            }
        }
        viewModelScope.launch {
            _subgenres.value = repository.getSubgenresSync(genreId)
        }
    }
}
