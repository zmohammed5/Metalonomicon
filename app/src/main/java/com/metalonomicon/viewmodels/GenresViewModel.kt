package com.metalonomicon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metalonomicon.data.entities.Genre
import com.metalonomicon.data.repository.MetalonomiconRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GenresViewModel(
    private val repository: MetalonomiconRepository
) : ViewModel() {

    val topLevelGenres: StateFlow<List<Genre>> = repository.getTopLevelGenres()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
