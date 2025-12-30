package com.metalonomicon.data.dao

import androidx.room.*
import com.metalonomicon.data.entities.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Update
    suspend fun update(album: Album)

    @Delete
    suspend fun delete(album: Album)

    @Query("SELECT * FROM albums WHERE albumId = :albumId")
    suspend fun getAlbumById(albumId: Long): Album?

    @Query("SELECT * FROM albums WHERE albumId = :albumId")
    fun getAlbumByIdFlow(albumId: Long): Flow<Album?>

    @Query("SELECT * FROM albums WHERE bandOwnerId = :bandId ORDER BY releaseYear ASC")
    fun getAlbumsByBand(bandId: Long): Flow<List<Album>>

    @Query("SELECT * FROM albums WHERE bandOwnerId = :bandId ORDER BY releaseYear ASC")
    suspend fun getAlbumsByBandSync(bandId: Long): List<Album>

    @Query("SELECT * FROM albums ORDER BY releaseYear DESC")
    fun getAllAlbums(): Flow<List<Album>>

    @Query("SELECT COUNT(*) FROM albums")
    suspend fun getAlbumCount(): Int

    @Query("""
        SELECT (releaseYear / 10) * 10 AS decade, COUNT(albumId) as count
        FROM albums
        GROUP BY decade
        ORDER BY decade ASC
    """)
    suspend fun getAlbumsByDecade(): List<DecadeCount>

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}

data class DecadeCount(
    val decade: Int,
    val count: Int
)
