package com.metalonomicon.data.dao

import androidx.room.*
import com.metalonomicon.data.entities.Band
import com.metalonomicon.data.models.BandWithAlbums
import com.metalonomicon.data.models.BandWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface BandDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(band: Band): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bands: List<Band>)

    @Update
    suspend fun update(band: Band)

    @Delete
    suspend fun delete(band: Band)

    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    suspend fun getBandById(bandId: Long): Band?

    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    fun getBandByIdFlow(bandId: Long): Flow<Band?>

    @Query("SELECT * FROM bands ORDER BY name ASC")
    fun getAllBands(): Flow<List<Band>>

    @Query("SELECT * FROM bands ORDER BY name ASC")
    suspend fun getAllBandsSync(): List<Band>

    @Transaction
    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    suspend fun getBandWithAlbums(bandId: Long): BandWithAlbums?

    @Transaction
    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    fun getBandWithAlbumsFlow(bandId: Long): Flow<BandWithAlbums?>

    @Transaction
    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    suspend fun getBandWithGenres(bandId: Long): BandWithGenres?

    @Transaction
    @Query("SELECT * FROM bands WHERE bandId = :bandId")
    fun getBandWithGenresFlow(bandId: Long): Flow<BandWithGenres?>

    @Query("SELECT COUNT(*) FROM bands")
    suspend fun getBandCount(): Int

    @Query("""
        SELECT countryOfOrigin, COUNT(bandId) as count
        FROM bands
        GROUP BY countryOfOrigin
        ORDER BY count DESC
        LIMIT :limit
    """)
    suspend fun getBandsByCountry(limit: Int = 10): List<CountryCount>

    @Query("DELETE FROM bands")
    suspend fun deleteAll()
}

data class CountryCount(
    val countryOfOrigin: String,
    val count: Int
)
