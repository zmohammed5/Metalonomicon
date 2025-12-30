package com.metalonomicon.data.dao

import androidx.room.*
import com.metalonomicon.data.entities.BandGenreCrossRef

@Dao
interface BandGenreCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: BandGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<BandGenreCrossRef>)

    @Delete
    suspend fun delete(crossRef: BandGenreCrossRef)

    @Query("DELETE FROM band_genre_cross_ref WHERE bandId = :bandId")
    suspend fun deleteAllForBand(bandId: Long)

    @Query("DELETE FROM band_genre_cross_ref WHERE genreId = :genreId")
    suspend fun deleteAllForGenre(genreId: Long)

    @Query("SELECT * FROM band_genre_cross_ref WHERE bandId = :bandId AND genreId = :genreId")
    suspend fun getCrossRef(bandId: Long, genreId: Long): BandGenreCrossRef?

    @Query("DELETE FROM band_genre_cross_ref")
    suspend fun deleteAll()

    @Query("""
        SELECT g.genreId, g.name, COUNT(bgc.bandId) as bandCount
        FROM genres g
        LEFT JOIN band_genre_cross_ref bgc ON g.genreId = bgc.genreId
        WHERE g.parentGenreId IS NULL
        GROUP BY g.genreId, g.name
        ORDER BY bandCount DESC
    """)
    suspend fun getGenreDistribution(): List<GenreDistribution>
}

data class GenreDistribution(
    val genreId: Long,
    val name: String,
    val bandCount: Int
)
