package com.metalonomicon.data.models

import androidx.room.Embedded
import androidx.room.Relation
import com.metalonomicon.data.entities.Album
import com.metalonomicon.data.entities.Band

data class BandWithAlbums(
    @Embedded val band: Band,
    @Relation(
        parentColumn = "bandId",
        entityColumn = "bandOwnerId"
    )
    val albums: List<Album>
)
