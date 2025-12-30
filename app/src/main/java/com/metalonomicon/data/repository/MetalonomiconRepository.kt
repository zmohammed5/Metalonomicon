package com.metalonomicon.data.repository

import com.metalonomicon.data.dao.*
import com.metalonomicon.data.entities.*
import com.metalonomicon.data.models.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MetalonomiconRepository(
    private val genreDao: GenreDao,
    private val bandDao: BandDao,
    private val albumDao: AlbumDao,
    private val crossRefDao: BandGenreCrossRefDao,
    private val searchDao: SearchDao
) {

    // Genre operations
    fun getTopLevelGenres(): Flow<List<Genre>> = genreDao.getTopLevelGenres()

    fun getSubgenres(parentId: Long): Flow<List<Genre>> = genreDao.getSubgenres(parentId)

    suspend fun getSubgenresSync(parentId: Long): List<Genre> = genreDao.getSubgenresSync(parentId)

    fun getGenreById(genreId: Long): Flow<Genre?> = genreDao.getGenreByIdFlow(genreId)

    fun getGenreWithBands(genreId: Long): Flow<GenreWithBands?> = genreDao.getGenreWithBandsFlow(genreId)

    // Band operations
    fun getAllBands(): Flow<List<Band>> = bandDao.getAllBands()

    fun getBandById(bandId: Long): Flow<Band?> = bandDao.getBandByIdFlow(bandId)

    fun getBandWithAlbums(bandId: Long): Flow<BandWithAlbums?> = bandDao.getBandWithAlbumsFlow(bandId)

    fun getBandWithGenres(bandId: Long): Flow<BandWithGenres?> = bandDao.getBandWithGenresFlow(bandId)

    // Album operations
    fun getAllAlbums(): Flow<List<Album>> = albumDao.getAllAlbums()

    fun getAlbumById(albumId: Long): Flow<Album?> = albumDao.getAlbumByIdFlow(albumId)

    fun getAlbumsByBand(bandId: Long): Flow<List<Album>> = albumDao.getAlbumsByBand(bandId)

    // Search operations
    fun searchBands(query: String): Flow<List<Band>> = searchDao.searchBands("$query*")

    fun searchGenres(query: String): Flow<List<Genre>> = searchDao.searchGenres("$query*")

    fun searchAlbums(query: String): Flow<List<Album>> = searchDao.searchAlbums("$query*")

    // Analytics operations
    suspend fun getTotalGenreCount(): Int = genreDao.getGenreCount()

    suspend fun getTotalBandCount(): Int = bandDao.getBandCount()

    suspend fun getTotalAlbumCount(): Int = albumDao.getAlbumCount()

    suspend fun getBandsByCountry(limit: Int = 10): List<CountryCount> = bandDao.getBandsByCountry(limit)

    suspend fun getAlbumsByDecade(): List<DecadeCount> = albumDao.getAlbumsByDecade()

    suspend fun getGenreDistribution(): List<GenreDistribution> = crossRefDao.getGenreDistribution()
}
