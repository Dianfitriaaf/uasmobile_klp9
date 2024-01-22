package com.bladerlaiga.catanime.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bladerlaiga.catanime.*

@Database(
  entities = [AnimeDetail::class, AnimeOverviewItem::class, AnimeFavorite::class, AnimeReminder::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract val animeDetailDAO: AnimeDetailDAO
  abstract val animeOverviewItemDAO: AnimeOverviewItemDAO
  abstract val animeFavoriteDAO: AnimeFavoriteDAO
  abstract val animeReminderDAO: AnimeReminderDAO

  companion object {
    private const val database_name = "app_database"

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      synchronized(this) {
        var instance = INSTANCE

        if (instance == null) {
          instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            database_name
          )
            .fallbackToDestructiveMigration()
            .build()
          INSTANCE = instance
        }
        return instance
      }
    }
  }
}
