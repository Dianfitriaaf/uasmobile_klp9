package com.bladerlaiga.catanime.models

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bladerlaiga.catanime.AnimeOverviewItem
import com.bladerlaiga.catanime.R
import com.bladerlaiga.catanime.repository.AppRepository
import kotlin.coroutines.suspendCoroutine

class HomeViewModel(
  application: Application
) : AndroidViewModel(application) {
  private var repository: AppRepository = AppRepository(application)
  private val animeService = repository.getAnimeService()
  private val animeOverviewItem = repository.getAnimeOverviewItem()
  private var data_year by mutableStateOf(2018)
  private var data_season by mutableStateOf("winter")

  var data by mutableStateOf(emptyList<AnimeOverviewItem>())
  var data_onload by mutableStateOf(false)
  var grid_type by mutableStateOf(GridType.List)
  var grid_count by mutableStateOf(1)
  var grid_id by mutableStateOf(R.drawable.ic_baseline_view_week_24)

  suspend fun load() {
    data_onload = true
    val result = animeOverviewItem.getAll()
    if (result.isEmpty()) {
      val overview = animeService.getOverview(data_year, data_season)
      animeOverviewItem.insert(anime = overview.anime.toTypedArray())
      data = overview.anime
    } else {
      data = result
    }
    data_onload = false
  }
  suspend fun loadMoreFromNetwork() {
    data_season = when(data_season) {
      "winter" -> "spring"
      "spring" -> "summer"
      "summer" -> "fall"
      else -> {
        data_year += 1
        "winter"
      }
    }
    load()
  }
  fun rotate() {
    if (grid_type == GridType.List) {
      grid_type = GridType.Column
      grid_count = 2
      grid_id = R.drawable.ic_round_view_module_24
    } else {
      grid_type = GridType.List
      grid_count = 1
      grid_id = R.drawable.ic_baseline_view_week_24
    }
  }
}

enum class GridType {
  List,
  Column
}