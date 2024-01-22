package com.bladerlaiga.catanime

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bladerlaiga.catanime.models.FavoriteViewModel
import com.bladerlaiga.catanime.models.GridType
import com.bladerlaiga.catanime.ui.components.*
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val favorite_title = "Favorite"

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FavoriteContent() {
  val app_name = stringResource(id = R.string.app_name)
  val viewModel = viewModel<FavoriteViewModel>()
  Surface(
    color = MaterialTheme.colors.background
  ) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
      scaffoldState = scaffoldState,
      topBar = {
        TopAppBar(
          title = { Text(favorite_title) },
          navigationIcon = {
            IconButton(onClick = { Route.navigator.back() }) {
              Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
          },
          actions = {
            IconButton(
              onClick = {
                viewModel.rotate()
              }
            ) {
              Icon(
                painter = painterResource(id = viewModel.grid_id),
                contentDescription = null
              )
            }
          }
        )
      },
      drawerContent = {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.proto_v2),
            modifier = Modifier.size(32.dp),
            contentDescription = null
          )
          Text(
            app_name,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column() {
          ListItem(
            text = { Text(home_title) },
            icon = {
              Icon(
                Icons.Filled.Home,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            },
            modifier = Modifier.clickable {
              Route.Home.navigate()
            }
          )
          ListItem(
            text = { Text(favorite_title) },
            icon = {
              Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            },
            modifier = Modifier.clickable {
              Route.Favorite.navigate()
            }
          )
          ListItem(
            text = { Text("Coming Soon") },
            icon = {
              Icon(
                Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
              )
            },
            modifier = Modifier.clickable { }
          )
        }
      },
      floatingActionButton = { }
    ) {
      LaunchedEffect(key1 = "load_data_favorite") {
        launch {
          try {
            viewModel.load()
          } catch (e: Exception) {
            e.message?.let { it1 -> Log.e("[F]", it1) }
            scaffoldState.snackbarHostState.showSnackbar("Failed Load Favorite")
          }
        }
      }
      if (!viewModel.data_loaded) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize(),
        ) {
          CircularProgressIndicator()
        }
      } else if (viewModel.data.isEmpty()) {
        LaunchedEffect(key1 = "favorite_notify") {
          launch {
            scaffoldState.snackbarHostState.showSnackbar("Favorite is Empty")
          }
        }
      } else {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyVerticalGrid(
          cells = GridCells.Fixed(viewModel.grid_count),
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          itemsIndexed(viewModel.data) { index, item ->
            val animeOverviewItem = item.animeOverviewItem
            val easing = lazyListState.calculateDelayAndEasing(index, viewModel.grid_count)
            val animation = tween<Float>(durationMillis = 240, delayMillis = 120, easing = easing)
            val transitionState = rememberTransitionScaleAndAlphaState()
            val transition = updateTransition(transitionState, label = "")
            val alpha by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
              when (state) {
                ScaleAndAlphaTransitionState.From -> 0F
                ScaleAndAlphaTransitionState.To -> 1F
              }
            }
            val scale by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
              when (state) {
                ScaleAndAlphaTransitionState.From -> 0F
                ScaleAndAlphaTransitionState.To -> 1F
              }
            }
//            val transitionState = rememberTransitionScaleAndAlphaState()
//            val open_transition = ScaleAndAlphaArgs(
//              fromScale = 2f,
//              toScale = 1f,
//              fromAlpha = 0f,
//              toAlpha = 1f,
//            )
//            val (alpha, scale) = TransitionScaleAndAlpha(
//              args = open_transition,
//              animation = animation
//            )
            Card(
              elevation = 4.dp,
              modifier = Modifier
                .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale)
                .clickable {
                  Route.Detail.navigate(animeOverviewItem.id)
                },
            ) {
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .zIndex(1F),
                contentAlignment = Alignment.TopEnd
              ) {
                IconButton(
                  onClick = {
                    coroutineScope.launch {
                      transitionState.targetState = ScaleAndAlphaTransitionState.From
                      delay(590)
//                      viewModel.mut_data.removeAt(index)
//                      viewModel.data = viewModel.mut_data.toList()
                      viewModel.data.removeAt(index)
//                      viewModel.data = viewModel.data
                      scaffoldState.snackbarHostState.showSnackbar("Success Remove Favorite")
                    }
                  }
                ) {
                  Icon(Icons.Filled.Favorite, contentDescription = null)
                }
              }
              if (viewModel.grid_type == GridType.List) {
                Row {
                  GlideImage(
                    imageModel = animeOverviewItem.image_url,
                    modifier = Modifier.fillParentMaxWidth(0.3F),
                    contentScale = ContentScale.Crop,
                    circularReveal = CircularReveal(duration = 280),
                    placeHolder = painterResource(id = R.drawable.ic_outline_image_24),
                    error = painterResource(id = R.drawable.ic_outline_broken_image_24)
                  )
                  Column(
                    modifier = Modifier
                      .fillParentMaxWidth(0.7F)
                      .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Text(
                      text = "Title : ${animeOverviewItem.title}",
                      fontSize = 16.sp,
                      textAlign = TextAlign.Left,
                      maxLines = 2,
                      softWrap = true,
                      modifier = Modifier
                        .fillMaxWidth()
                    )
                    Text(
                      text = "Genre : ${animeOverviewItem.genres.joinToString { it.name }}",
                      fontSize = 16.sp,
                      textAlign = TextAlign.Left,
                      maxLines = 2,
                      softWrap = true,
                      modifier = Modifier
                        .fillMaxWidth()
                    )
                    Text(
                      text = "Episode : ${animeOverviewItem.episodes}",
                      fontSize = 16.sp,
                      textAlign = TextAlign.Left,
                      maxLines = 1,
                      softWrap = true,
                      modifier = Modifier
                        .fillMaxWidth()
                    )
                    Text(
                      text = "Source : ${animeOverviewItem.source}",
                      fontSize = 16.sp,
                      textAlign = TextAlign.Left,
                      maxLines = 1,
                      softWrap = true,
                      modifier = Modifier
                        .fillMaxWidth()
                    )
                  }
                }
              } else {
                Column(
                  verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                  GlideImage(
                    imageModel = animeOverviewItem.image_url,
                    modifier = Modifier.fillParentMaxWidth(),
                    contentScale = ContentScale.Crop,
                    circularReveal = CircularReveal(duration = 280),
                    placeHolder = painterResource(id = R.drawable.ic_outline_image_24),
                    error = painterResource(id = R.drawable.ic_outline_broken_image_24)
                  )
                  Text(
                    text = animeOverviewItem.title,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    softWrap = true,
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 16.dp)
                  )
                }
              }
            }
          }
          /*item(span = {
            GridItemSpan(viewModel.grid_count)
          }) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier.fillParentMaxWidth()
            ) {
              if (viewModel.data_onload) {
                CircularProgressIndicator()
              } else {
                Button(onClick = {
                  coroutineScope.launch {
                    viewModel.loadMoreFromNetwork()
                  }
                }) {
                  Text(text = "More")
                }
              }
            }
          }*/
        }
      }
    }
  }
}