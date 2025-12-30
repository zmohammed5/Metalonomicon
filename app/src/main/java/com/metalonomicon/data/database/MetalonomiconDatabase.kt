package com.metalonomicon.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.metalonomicon.data.dao.*
import com.metalonomicon.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@Database(
    entities = [
        Genre::class,
        Band::class,
        Album::class,
        BandGenreCrossRef::class,
        GenreFts::class,
        BandFts::class,
        AlbumFts::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MetalonomiconDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao
    abstract fun bandDao(): BandDao
    abstract fun albumDao(): AlbumDao
    abstract fun bandGenreCrossRefDao(): BandGenreCrossRefDao
    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile
        private var INSTANCE: MetalonomiconDatabase? = null

        fun getDatabase(context: Context): MetalonomiconDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetalonomiconDatabase::class.java,
                    "metalonomicon_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database, context)
                }
            }
        }

        suspend fun populateDatabase(database: MetalonomiconDatabase, context: Context) {
            val genreDao = database.genreDao()
            val bandDao = database.bandDao()
            val albumDao = database.albumDao()
            val crossRefDao = database.bandGenreCrossRefDao()

            try {
                // Read and parse JSON from assets
                val inputStream = context.assets.open("prepopulate.json")
                val reader = InputStreamReader(inputStream)
                val seedData = Gson().fromJson(reader, SeedData::class.java)
                reader.close()

                // Insert genres
                val genreIdMap = mutableMapOf<Long, Long>()
                seedData.genres.forEach { genreJson ->
                    val genre = Genre(
                        genreId = 0, // Auto-generate
                        name = genreJson.name,
                        description = genreJson.description,
                        eraOfOrigin = genreJson.eraOfOrigin,
                        parentGenreId = genreJson.parentGenreId
                    )
                    val newId = genreDao.insert(genre)
                    genreIdMap[genreJson.genreId] = newId
                }

                // Insert bands
                val bandIdMap = mutableMapOf<Long, Long>()
                seedData.bands.forEach { bandJson ->
                    val band = Band(
                        bandId = 0, // Auto-generate
                        name = bandJson.name,
                        countryOfOrigin = bandJson.countryOfOrigin,
                        status = bandJson.status,
                        bio = bandJson.bio
                    )
                    val newId = bandDao.insert(band)
                    bandIdMap[bandJson.bandId] = newId
                }

                // Insert albums
                seedData.albums.forEach { albumJson ->
                    val actualBandId = bandIdMap[albumJson.bandOwnerId] ?: return@forEach
                    val album = Album(
                        albumId = 0, // Auto-generate
                        title = albumJson.title,
                        releaseYear = albumJson.releaseYear,
                        bandOwnerId = actualBandId,
                        albumType = albumJson.albumType
                    )
                    albumDao.insert(album)
                }

                // Insert band-genre relationships
                seedData.bandGenreRefs.forEach { refJson ->
                    val actualBandId = bandIdMap[refJson.bandId] ?: return@forEach
                    val actualGenreId = genreIdMap[refJson.genreId] ?: return@forEach
                    val crossRef = BandGenreCrossRef(
                        bandId = actualBandId,
                        genreId = actualGenreId
                    )
                    crossRefDao.insert(crossRef)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                // Log error but don't crash
            }
        }
    }
}

// JSON data classes for parsing
data class SeedData(
    val genres: List<GenreJson>,
    val bands: List<BandJson>,
    val albums: List<AlbumJson>,
    @SerializedName("band_genre_refs")
    val bandGenreRefs: List<BandGenreRefJson>
)

data class GenreJson(
    val genreId: Long,
    val name: String,
    val description: String,
    val eraOfOrigin: String,
    val parentGenreId: Long?
)

data class BandJson(
    val bandId: Long,
    val name: String,
    val countryOfOrigin: String,
    val status: String,
    val bio: String
)

data class AlbumJson(
    val albumId: Long,
    val title: String,
    val releaseYear: Int,
    val bandOwnerId: Long,
    val albumType: String
)

data class BandGenreRefJson(
    val bandId: Long,
    val genreId: Long
)
