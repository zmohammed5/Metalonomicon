# The Metalonomicon

A production-quality Android encyclopedia of heavy metal music, demonstrating advanced Android development patterns with 100% offline functionality.

## Technical Overview

This application showcases:
- Complex relational database design with Room
- Full-text search implementation
- MVVM architecture with reactive data streams
- Modern Android UI with Jetpack Compose
- Complete offline functionality with bundled data

## Architecture

### Database Layer
**Room Database** with complex relational schema:
- 4 core entities (Genre, Band, Album, BandGenreCrossRef)
- 3 FTS4 entities for full-text search
- Self-referencing foreign keys for hierarchical genres
- Many-to-many relationships via junction tables
- One-to-many relationships with cascade deletion

**Complex Queries:**
```kotlin
@Transaction
@Query("SELECT * FROM genres WHERE genreId = :genreId")
fun getGenreWithBands(genreId: Long): Flow<GenreWithBands?>

@Query("""
    SELECT (releaseYear / 10) * 10 AS decade, COUNT(albumId) as count
    FROM albums GROUP BY decade ORDER BY decade ASC
""")
suspend fun getAlbumsByDecade(): List<DecadeCount>
```

### Data Flow
```
Database → Repository → ViewModel (StateFlow) → Compose UI
```

- **Repository Pattern**: Single source of truth for data access
- **Kotlin Coroutines**: All database operations are suspending functions
- **StateFlow**: Reactive UI updates via Compose's `collectAsState()`
- **Flow Operators**: `debounce`, `flatMapLatest` for search optimization

### UI Architecture
**100% Jetpack Compose** with Material3:
- Custom theme implementing dark gothic aesthetic
- Bottom navigation with NavHost
- Nested navigation for hierarchical data
- Reusable composable components
- Canvas-based data visualization

## Key Features

### 1. Hierarchical Genre Browser
Navigate through metal subgenres with parent-child relationships:
- Top-level genres (Heavy Metal, Thrash, Death Metal, etc.)
- Subgenres (Melodic Death Metal, Atmospheric Black Metal, etc.)
- Genre descriptions with historical context
- Band listings per genre

### 2. Real-time Full-Text Search
FTS4-powered search across all entities:
```kotlin
@Query("""
    SELECT bands.* FROM bands
    JOIN bands_fts ON bands.rowid = bands_fts.rowid
    WHERE bands_fts MATCH :query
""")
fun searchBands(query: String): Flow<List<Band>>
```
- 300ms debounce to minimize queries
- Concurrent search across bands, albums, and genres
- Categorized result display

### 3. Analytics Dashboard
Custom data visualizations:
- Bands by country (aggregated query)
- Albums by decade (GROUP BY with computed columns)
- Genre distribution (JOIN with COUNT)
- Canvas-drawn bar charts

### 4. Detailed Band Profiles
- Complete discography sorted by year
- Multiple genre associations
- Country and status tracking
- Biographical information

## Data Model

### Entities
```kotlin
@Entity(tableName = "genres", foreignKeys = [...])
data class Genre(
    @PrimaryKey val genreId: Long,
    val name: String,
    val description: String,
    val eraOfOrigin: String,
    val parentGenreId: Long?  // Self-referencing FK
)

@Entity(tableName = "band_genre_cross_ref", primaryKeys = ["bandId", "genreId"])
data class BandGenreCrossRef(
    val bandId: Long,
    val genreId: Long
)
```

### Relationships
```kotlin
data class BandWithAlbums(
    @Embedded val band: Band,
    @Relation(parentColumn = "bandId", entityColumn = "bandOwnerId")
    val albums: List<Album>
)

data class GenreWithBands(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "bandId",
        associateBy = Junction(BandGenreCrossRef::class)
    )
    val bands: List<Band>
)
```

## Database Seeding

Automatic population on first launch via `RoomDatabase.Callback`:
```kotlin
override fun onCreate(db: SupportSQLiteDatabase) {
    val seedData = Gson().fromJson(reader, SeedData::class.java)
    // Insert genres, map IDs
    // Insert bands, map IDs
    // Insert albums with FK references
    // Insert junction table entries
}
```

**Included Data:**
- 15 genres (10 top-level + 5 subgenres)
- 45 bands with detailed biographies
- 154 albums spanning 1970-2018
- Historically accurate data with proper relationships

## Technical Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| Database | Room 2.6.1 |
| Async | Kotlin Coroutines + Flow |
| DI | Manual (no framework) |
| Build | Gradle (KTS) + KSP |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 (Android 14) |

## Privacy & Offline

- **No internet permission** - fully functional offline
- **No analytics or telemetry**
- All data bundled as JSON asset
- No external API dependencies

## Project Structure

```
app/src/main/
├── java/com/metalonomicon/
│   ├── data/
│   │   ├── entities/          # Room entities (@Entity, @Fts4)
│   │   ├── dao/               # Data Access Objects
│   │   ├── models/            # Relationship POGOs
│   │   ├── database/          # Database + seeding callback
│   │   └── repository/        # Repository pattern
│   ├── viewmodels/            # ViewModels with StateFlow
│   ├── ui/
│   │   ├── screens/           # Composable screens
│   │   ├── components/        # Reusable UI components
│   │   ├── navigation/        # Navigation setup
│   │   └── theme/             # Material3 theme
│   ├── MainActivity.kt
│   └── MetalonomiconApplication.kt
└── assets/
    └── prepopulate.json       # Database seed data
```

## Build Instructions

1. Clone the repository
2. Open in Android Studio Hedgehog or later
3. Sync Gradle
4. Run on emulator or device (API 26+)

No API keys or configuration required.

## Code Quality

- No TODO comments - fully implemented
- Proper error handling in database seeding
- Consistent naming conventions
- Type-safe navigation with sealed classes
- Separation of concerns across layers

## Demonstrated Skills

- Complex Room database schema design
- Full-text search implementation
- MVVM architecture with reactive patterns
- Jetpack Compose UI development
- Kotlin Coroutines and Flow
- Material3 theming and design
- Navigation Compose
- Custom Canvas drawing
- JSON parsing and data seeding
- Offline-first architecture
