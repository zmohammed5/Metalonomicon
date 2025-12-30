package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Band::class)
@Entity(tableName = "bands_fts")
data class BandFts(
    val name: String,
    val countryOfOrigin: String,
    val bio: String
)
