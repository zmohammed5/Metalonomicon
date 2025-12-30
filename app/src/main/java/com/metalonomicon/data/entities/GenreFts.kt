package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Genre::class)
@Entity(tableName = "genres_fts")
data class GenreFts(
    val name: String,
    val description: String
)
