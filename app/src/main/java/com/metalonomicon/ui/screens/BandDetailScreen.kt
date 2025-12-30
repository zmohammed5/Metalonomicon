package com.metalonomicon.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metalonomicon.data.entities.Album
import com.metalonomicon.ui.components.AlbumCard
import com.metalonomicon.viewmodels.BandDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandDetailScreen(
    bandId: Long,
    viewModel: BandDetailViewModel,
    onBackClick: () -> Unit,
    onGenreClick: (Long) -> Unit
) {
    val bandWithAlbums by viewModel.bandWithAlbums.collectAsState()
    val bandWithGenres by viewModel.bandWithGenres.collectAsState()

    LaunchedEffect(bandId) {
        viewModel.loadBandDetails(bandId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = bandWithAlbums?.band?.name ?: "Loading...",
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
        bandWithAlbums?.let { bandData ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Band Info
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Country",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = bandData.band.countryOfOrigin,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Status",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = bandData.band.status,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = when(bandData.band.status) {
                                            "Active" -> MaterialTheme.colorScheme.primary
                                            "Split-up" -> MaterialTheme.colorScheme.error
                                            else -> MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Genres
                bandWithGenres?.let { genreData ->
                    if (genreData.genres.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Genres",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    genreData.genres.forEach { genre ->
                                        Text(
                                            text = "• ${genre.name}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier
                                                .clickable { onGenreClick(genre.genreId) }
                                                .padding(vertical = 4.dp),
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Biography
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Biography",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = bandData.band.bio,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Discography
                item {
                    Text(
                        text = "Discography (${bandData.albums.size})",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(bandData.albums.sortedBy { it.releaseYear }) { album ->
                    AlbumCard(album = album)
                }
            }
        }
    }
}
