package com.metalonomicon.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.metalonomicon.data.entities.Band
import com.metalonomicon.data.entities.BandGenreCrossRef
import com.metalonomicon.data.entities.Genre

data class BandWithGenres(
    @Embedded val band: Band,
    @Relation(
        parentColumn = "bandId",
        entityColumn = "genreId",
        associateBy = Junction(BandGenreCrossRef::class)
    )
    val genres: List<Genre>
)
