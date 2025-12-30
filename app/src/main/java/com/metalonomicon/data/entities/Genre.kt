package com.metalonomicon.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    foreignKeys = [
        ForeignKey(
            entity = Genre::class,
            parentColumns = ["genreId"],
            childColumns = ["parentGenreId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["parentGenreId"])]
)
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val genreId: Long = 0,

    val name: String,

    val description: String,

    val eraOfOrigin: String,

    val parentGenreId: Long? = null
)
