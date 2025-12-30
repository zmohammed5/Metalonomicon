package com.metalonomicon.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.metalonomicon.data.entities.Band
import com.metalonomicon.data.entities.BandGenreCrossRef
import com.metalonomicon.data.entities.Genre

data class GenreWithBands(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "bandId",
        associateBy = Junction(BandGenreCrossRef::class)
    )
    val bands: List<Band>
)
