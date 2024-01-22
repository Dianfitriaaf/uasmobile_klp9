package com.bladerlaiga.catanime.database

import androidx.room.*
import com.bladerlaiga.catanime.*

@Dao
interface AnimeDetailDAO {
  @Query(value = "Select * From anime_detail Where id = :id")
  suspend fun get(id: Long): AnimeDetail?

  @Query(value = "Select * From anime_detail")
  suspend fun getAll(): List<AnimeDetail>

  @Insert
  suspend fun insert(vararg anime: AnimeDetail)

  @Update
  suspend fun update(vararg anime: AnimeDetail)

  @Delete
  suspend fun delete(vararg anime: AnimeDetail)
}

@Dao
interface AnimeOverviewItemDAO {
  @Query(value = "Select * From anime_overview_item Where id = :id")
  suspend fun get(id: Long): AnimeOverviewItem?

  @Query(value = "Select * From anime_overview_item")
  suspend fun getAll(): List<AnimeOverviewItem>

  @Insert
  suspend fun insert(vararg anime: AnimeOverviewItem)

  @Update
  suspend fun update(vararg anime: AnimeOverviewItem)

  @Delete
  suspend fun delete(vararg anime: AnimeOverviewItem)
}

@Dao
interface AnimeFavoriteDAO {
  @Query("Select * From anime_favorite Where id = :id")
  suspend fun get(id: Long): AnimeFavorite?

  @Query("Select * From anime_favorite")
  suspend fun getAll(): List<AnimeFavorite>

  @Query("Select * From anime_favorite Where id_anime_overview_item = :id_anime_overview_item")
  suspend fun getByIdAnimeOverviewItem(id_anime_overview_item: Long): AnimeFavorite?

  @Transaction
  @Query("Select * From anime_favorite")
  suspend fun getWithOverviewItemAll(): List<AnimeFavoriteAndOverviewItem>

  @Insert
  suspend fun insert(vararg values: AnimeFavorite)

  @Update
  suspend fun update(vararg values: AnimeFavorite)

  @Delete
  suspend fun delete(vararg values: AnimeFavorite)
}

@Dao
interface AnimeReminderDAO {
  @Query("Select * From anime_reminder Where id = :id")
  suspend fun get(id: Long): AnimeReminder?

  @Query("Select * From anime_reminder")
  suspend fun getAll(): List<AnimeReminder>

  @Query("Select * From anime_reminder Where id_anime_detail = :id_anime_detail")
  suspend fun getByIdAnimeDetail(id_anime_detail: Long): AnimeReminder?

  @Transaction
  @Query("Select * From anime_reminder Where id = :id")
  suspend fun getByIdWithAnimeDetail(id: Long): AnimeReminderAndDetail?

  @Transaction
  @Query("Select * From anime_reminder Where id_anime_detail = :id_anime_detail")
  suspend fun getByIdAnimeDetailWithAnimeDetail(id_anime_detail: Long): AnimeReminderAndDetail?

  @Insert
  suspend fun insert(vararg values: AnimeReminder)

  @Update
  suspend fun update(vararg values: AnimeReminder)

  @Delete
  suspend fun delete(vararg values: AnimeReminder)
}