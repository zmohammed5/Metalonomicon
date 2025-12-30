package com.metalonomicon.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metalonomicon.data.entities.Band
import com.metalonomicon.data.entities.Genre
import com.metalonomicon.ui.components.BandCard
import com.metalonomicon.ui.components.GenreCard
import com.metalonomicon.viewmodels.GenreDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreDetailScreen(
    genreId: Long,
    viewModel: GenreDetailViewModel,
    onBackClick: () -> Unit,
    onBandClick: (Long) -> Unit,
    onSubgenreClick: (Long) -> Unit
) {
    val genreWithBands by viewModel.genreWithBands.collectAsState()
    val subgenres by viewModel.subgenres.collectAsState()

    LaunchedEffect(genreId) {
        viewModel.loadGenreDetails(genreId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = genreWithBands?.genre?.name ?: "Loading...",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        genreWithBands?.let { data ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Genre Description
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Era of Origin",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = data.genre.eraOfOrigin,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = data.genre.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Subgenres
                if (subgenres.isNotEmpty()) {
                    item {
                        Text(
                            text = "Subgenres",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(subgenres) { subgenre ->
                        GenreCard(
                            genre = subgenre,
                            onClick = { onSubgenreClick(subgenre.genreId) }
                        )
                    }
                }

                // Bands
                item {
                    Text(
                        text = "Bands (${data.bands.size})",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(data.bands) { band ->
                    BandCard(
                        band = band,
                        onClick = { onBandClick(band.bandId) }
                    )
                }
            }
        }
    }
}
