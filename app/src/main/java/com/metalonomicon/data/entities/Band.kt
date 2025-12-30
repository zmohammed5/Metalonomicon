package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bands")
data class Band(
    @PrimaryKey(autoGenerate = true)
    val bandId: Long = 0,

    val name: String,

    val countryOfOrigin: String,

    val status: String, // Active, Split-up, On Hold, etc.

    val bio: String
)
