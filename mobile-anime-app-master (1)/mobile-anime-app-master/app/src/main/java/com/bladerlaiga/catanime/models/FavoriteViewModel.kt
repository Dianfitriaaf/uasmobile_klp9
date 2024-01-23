package com.bladerlaiga.catanime.models

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bladerlaiga.catanime.AnimeFavoriteAndOverviewItem
import com.bladerlaiga.catanime.R
import com.bladerlaiga.catanime.repository.AppRepository

class FavoriteViewModel(
  application: Application
) : AndroidViewModel(application) {
  private var repository: AppRepository = AppRepository(application)
  private val animeFavoriteDAO = repository.getAnimeFavorite()

//  var mut_data = mutableListOf<AnimeFavoriteAndOverviewItem>()
  var data = mutableStateListOf<AnimeFavoriteAndOverviewItem>()
//  var data by mutableStateOf<List<AnimeFavoriteAndOverviewItem>>(emptyList())
  var data_loaded by mutableStateOf(false)
  var grid_type by mutableStateOf(GridType.List)
  var grid_count by mutableStateOf(1)
  var grid_id by mutableStateOf(R.drawable.ic_baseline_view_week_24)

  suspend fun load() {
//    mut_data = animeFavoriteDAO.getWithOverviewItemAll().toMutableStateList()
    data = animeFavoriteDAO.getWithOverviewItemAll().toMutableStateList()
//    data = animeFavoriteDAO.getWithOverviewItemAll()
//    mut_data = data.toMutableList()
    data_loaded = true
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