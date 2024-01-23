package com.bladerlaiga.catanime.repository


import android.content.Context
import com.bladerlaiga.catanime.database.*
import com.bladerlaiga.catanime.network.AnimeService

class AppRepository(context: Context) {
  private var animeDetailDAO: AnimeDetailDAO
  private var animeOverviewItemDAO: AnimeOverviewItemDAO
  private var animeFavoriteDAO: AnimeFavoriteDAO
  private var animeService: AnimeService
  private var animeReminderDAO: AnimeReminderDAO

  init {
    val database = AppDatabase.getInstance(context)

    animeDetailDAO = database.animeDetailDAO
    animeOverviewItemDAO = database.animeOverviewItemDAO
    animeFavoriteDAO = database.animeFavoriteDAO
    animeReminderDAO = database.animeReminderDAO
    animeService = AnimeService.getInstance()
  }

  fun getAnimeDetail(): AnimeDetailDAO {
    return animeDetailDAO
  }

  fun getAnimeOverviewItem(): AnimeOverviewItemDAO {
    return animeOverviewItemDAO
  }

  fun getAnimeFavorite(): AnimeFavoriteDAO {
    return animeFavoriteDAO
  }

  fun getAnimeReminder(): AnimeReminderDAO {
    return animeReminderDAO
  }

  fun getAnimeService(): AnimeService {
    return animeService
  }
}