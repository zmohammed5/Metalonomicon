package com.metalonomicon.data.dao

import androidx.room.*
import com.metalonomicon.data.entities.Genre
import com.metalonomicon.data.models.GenreWithBands
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<Genre>)

    @Update
    suspend fun update(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)

    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getGenreById(genreId: Long): Genre?

    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    fun getGenreByIdFlow(genreId: Long): Flow<Genre?>

    @Query("SELECT * FROM genres ORDER BY name ASC")
    fun getAllGenres(): Flow<List<Genre>>

    @Query("SELECT * FROM genres WHERE parentGenreId IS NULL ORDER BY name ASC")
    fun getTopLevelGenres(): Flow<List<Genre>>

    @Query("SELECT * FROM genres WHERE parentGenreId = :parentId ORDER BY name ASC")
    fun getSubgenres(parentId: Long): Flow<List<Genre>>

    @Query("SELECT * FROM genres WHERE parentGenreId = :parentId ORDER BY name ASC")
    suspend fun getSubgenresSync(parentId: Long): List<Genre>

    @Transaction
    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getGenreWithBands(genreId: Long): GenreWithBands?

    @Transaction
    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    fun getGenreWithBandsFlow(genreId: Long): Flow<GenreWithBands?>

    @Query("SELECT COUNT(*) FROM genres")
    suspend fun getGenreCount(): Int

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}
