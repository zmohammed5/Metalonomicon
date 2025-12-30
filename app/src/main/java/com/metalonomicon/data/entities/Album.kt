package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = Band::class,
            parentColumns = ["bandId"],
            childColumns = ["bandOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["bandOwnerId"])]
)
data class Album(
    @PrimaryKey(autoGenerate = true)
    val albumId: Long = 0,

    val title: String,

    val releaseYear: Int,

    val bandOwnerId: Long,

    val albumType: String // Studio, Live, EP, Compilation, Demo, etc.
)
