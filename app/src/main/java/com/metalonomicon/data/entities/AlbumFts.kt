package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Album::class)
@Entity(tableName = "albums_fts")
data class AlbumFts(
    val title: String,
    val albumType: String
)
