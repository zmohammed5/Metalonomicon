package com.metalonomicon.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.metalonomicon.data.entities.Album
import com.metalonomicon.data.entities.Band
import com.metalonomicon.data.entities.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("""
        SELECT bands.* FROM bands
        JOIN bands_fts ON bands.rowid = bands_fts.rowid
        WHERE bands_fts MATCH :query
        ORDER BY bands.name ASC
    """)
    fun searchBands(query: String): Flow<List<Band>>

    @Query("""
        SELECT genres.* FROM genres
        JOIN genres_fts ON genres.rowid = genres_fts.rowid
        WHERE genres_fts MATCH :query
        ORDER BY genres.name ASC
    """)
    fun searchGenres(query: String): Flow<List<Genre>>

    @Query("""
        SELECT albums.* FROM albums
        JOIN albums_fts ON albums.rowid = albums_fts.rowid
        WHERE albums_fts MATCH :query
        ORDER BY albums.title ASC
    """)
    fun searchAlbums(query: String): Flow<List<Album>>
}
