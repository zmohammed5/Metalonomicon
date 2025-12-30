package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "band_genre_cross_ref",
    primaryKeys = ["bandId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = Band::class,
            parentColumns = ["bandId"],
            childColumns = ["bandId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["genreId"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["bandId"]),
        Index(value = ["genreId"])
    ]
)
data class BandGenreCrossRef(
    val bandId: Long,
    val genreId: Long
)
