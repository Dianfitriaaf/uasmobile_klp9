package com.bladerlaiga.catanime.models

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import com.bladerlaiga.catanime.AlarmReceiver
import com.bladerlaiga.catanime.AnimeDetail
import com.bladerlaiga.catanime.AnimeFavorite
import com.bladerlaiga.catanime.AnimeReminder
import com.bladerlaiga.catanime.repository.AppRepository

class DetailViewModel(
  application: Application
) : AndroidViewModel(application) {
  private var repository: AppRepository = AppRepository(application)
  private val animeDetail = repository.getAnimeDetail()
  private val animeFavorite = repository.getAnimeFavorite()
  private val animeReminder = repository.getAnimeReminder()
  private val animeService = repository.getAnimeService()
  private var favorite: AnimeFavorite? = null
  private var reminder: AnimeReminder? = null
  private lateinit var data: AnimeDetail
  private var data_id = 0L
  var title by mutableStateOf("")
  var image by mutableStateOf("")
  var title_alt by mutableStateOf("")
  var episode by mutableStateOf("")
  var status by mutableStateOf("")
  var aired by mutableStateOf("")
  var premiered by mutableStateOf("")
  var source by mutableStateOf("")
  var producer by mutableStateOf("")
  var studio by mutableStateOf("")
  var genre by mutableStateOf("")
  var theme by mutableStateOf("")
  var duration by mutableStateOf("")
  var rating by mutableStateOf("")
  var synopsis by mutableStateOf("")
  var is_favorite by mutableStateOf(false)
  var is_reminder by mutableStateOf(false)

  suspend fun load(id: Long) {
    val result = animeDetail.get(id)
    if (result != null) {
      data = result
      favorite = animeFavorite.getByIdAnimeOverviewItem(id_anime_overview_item = id)
      reminder = animeReminder.getByIdAnimeDetail(id_anime_detail = id)
      is_favorite = favorite != null
      is_reminder = reminder != null
    } else {
      data = animeService.getSeason(id = id)
      animeDetail.insert(
        AnimeDetail(
          id = data.id,
          themes = data.themes,
          genres = data.genres,
          producers = data.producers,
          source = data.source,
          episodes = data.episodes,
          score = data.score,
          type = data.type,
          synopsis = data.synopsis,
          image_url = data.image_url,
          title = data.title,
          duration = data.duration,
          status = data.status,
          aired = data.aired,
          airing = data.airing,
          premiered = data.premiered,
          rating = data.rating,
          related = data.related,
          studios = data.studios,
          title_alt = data.title_alt,
          trailer_url = data.trailer_url,
          url = data.url
        )
      )
    }
    data_id = data.id
    title = data.title
    image = data.image_url
    title_alt = ""/*data.title_alt*/
    episode = data.episodes.toString()
    status = data.status
    aired = data.aired.string
    premiered = data.premiered
    source = data.source
    producer = data.producers.joinToString { it.name }
    studio = data.studios.joinToString { it.name }
    genre = data.genres.joinToString { it.name }
    theme = data.themes.joinToString { it.name }
    duration = data.duration
    rating = data.rating
    synopsis = data.synopsis
  }

  suspend fun add_avorite() {
    if (data_id == 0L) {
      throw Exception("Data not Found")
    }
    animeFavorite.insert(
      AnimeFavorite(id_anime_overview_item = data_id)
    )
    is_favorite = true
  }

  suspend fun del_favorite() {
    favorite?.let { animeFavorite.delete(it) }
    is_favorite = false
  }

  suspend fun add_reminder() {
    animeReminder.insert(
      AnimeReminder(
        id_anime_detail = data_id,
      )
    )
    val context = this.getApplication<Application>()
    val intent = Intent(context, AlarmReceiver::class.java).apply {
      putExtra("id", data_id)
    }
    context.sendBroadcast(intent)
    is_reminder = true
  }

  suspend fun del_reminder() {
    reminder?.let { animeReminder.delete(it) }
    is_reminder = false
  }
}