package com.bladerlaiga.catanime.models

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bladerlaiga.catanime.AnimeOverviewItem
import com.bladerlaiga.catanime.Genre
import com.bladerlaiga.catanime.Producer
import com.bladerlaiga.catanime.Theme
import com.bladerlaiga.catanime.repository.AppRepository

class EditViewModel(
  application: Application
) : AndroidViewModel(application) {
  private var repository: AppRepository = AppRepository(application)
  private val animeDAO = repository.getAnimeOverviewItem()

  var title by mutableStateOf("")
  var image by mutableStateOf("")
  var bitmap by mutableStateOf<Bitmap?>(null)
  var alt_name by mutableStateOf("")
  var episode by mutableStateOf(0)
  var status by mutableStateOf("")
  var aired by mutableStateOf("")
  var premiered by mutableStateOf("")
  var source by mutableStateOf("")
  var producer by mutableStateOf("")
  var studios by mutableStateOf("")
  var genre by mutableStateOf("")
  var theme by mutableStateOf("")
  var duration by mutableStateOf("")
  var synopsis by mutableStateOf("")

  fun reset() {
    title = ""
    image = ""
    bitmap = null
    alt_name = ""
    episode = 0
    status = ""
    aired = ""
    premiered = ""
    source = ""
    producer = ""
    studios = ""
    genre = ""
    theme = ""
    duration = ""
    synopsis = ""
  }
  suspend fun save() {
    animeDAO.insert(
      AnimeOverviewItem(
        title = title,
        image_url = image,
        episodes = episode,
//        airing_start = aired,
        source = source,
        producers = producer.split(", ").map { Producer(it) },
        genres = genre.split(", ").map { Genre(it) },
        themes = theme.split(", ").map { Theme(it) },
        synopsis = synopsis,
      )
    )
  }
}