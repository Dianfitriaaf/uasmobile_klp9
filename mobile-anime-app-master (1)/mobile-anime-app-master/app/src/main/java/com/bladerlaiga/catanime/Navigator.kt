package com.bladerlaiga.catanime

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.*

class Navigator {
  private val _sharedFlow = sharedFlow.asSharedFlow()
  fun getSharedFlow(): SharedFlow<NavigatorMessage> {
    return _sharedFlow
  }

  fun navigate(path: String): Boolean {
    NavigatorMessage.name = "n"
    NavigatorMessage.arg = path
    return sharedFlow.tryEmit(NavigatorMessage)
  }

  fun back(): Boolean {
    NavigatorMessage.name = "b"
    return sharedFlow.tryEmit(NavigatorMessage)
  }

  companion object {
    private val sharedFlow = MutableSharedFlow<NavigatorMessage>(extraBufferCapacity = 1)
  }
}

object NavigatorMessage {
  var name: String = ""
  var arg: Any = ""
}

sealed class Route(var path: String) {
  object Home : Route("/home") {  }
  object Edit : Route("/edit") {  }
  object Detail : Route("/detail/{id}") {
    fun navigate(id: Long): Boolean {
      return navigator.navigate("/detail/$id")
    }
  }
  object ComingSoon : Route("/coming-soon")
  object Favorite : Route("/favorite") {  }
  open fun navigate(): Boolean {
    return navigator.navigate(path)
  }
  open fun back(): Boolean {
    return navigator.back()
  }
  companion object {
    val navigator = Navigator()
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavigatorContent() {
  val navController = rememberNavController()
  LaunchedEffect("navigation") {
    Route.navigator.getSharedFlow().onEach {
      if (it.name == "n") {
        navController.navigate((it.arg as String))
      } else if (it.name == "b") {
        navController.popBackStack()
      }
    }.launchIn(this)
  }
  NavHost(
    navController = navController,
    startDestination = Route.Home.path
  ) {
    composable(Route.Home.path) {
      HomeContent()
    }
    composable(Route.Edit.path) {
      EditContent()
    }
    composable(
      route = Route.Detail.path,
      arguments = listOf(
        navArgument(name = "id") {
          type = NavType.LongType
        }
      )
    ) {
      val id = it.arguments?.getLong("id")
      Text(text = id.toString())
      if (id != null) {
        DetailContent(id)
      } else {
        navController.popBackStack()
      }
    }
    composable(Route.Favorite.path) {
      FavoriteContent()
    }
    composable(Route.ComingSoon.path) {
    }
  }
}
