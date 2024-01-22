package com.bladerlaiga.catanime

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bladerlaiga.catanime.models.DetailViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterialApi
@Composable
fun DetailContent(id: Long) {
  val app_name = stringResource(id = R.string.app_name)
  val viewModel = viewModel<DetailViewModel>()
  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  var openDialog by remember { mutableStateOf(false) }
  var loaded by remember { mutableStateOf(false) }
  Surface(
    color = MaterialTheme.colors.background
  ) {
    Scaffold(
      scaffoldState = scaffoldState,
      topBar = {
        TopAppBar(
          title = {
            Text(
              text = viewModel.title,
              maxLines = 1,
              softWrap = true
            )
          },
          navigationIcon = {
            IconButton(onClick = { Route.navigator.back() }) {
              Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
          },
          actions = {
            IconButton(onClick = {
              coroutineScope.launch {
                try {
                  if (viewModel.is_favorite) {
                    viewModel.del_favorite()
                    scaffoldState.snackbarHostState.showSnackbar("Success Remove from Favorite")
                  } else {
                    viewModel.add_avorite()
                    scaffoldState.snackbarHostState.showSnackbar("Success Add to Favorite")
                  }
                } catch (e: Exception) {
                  if (viewModel.is_favorite) {
                    scaffoldState.snackbarHostState.showSnackbar("Failed Remove from Favorite")
                  } else {
                    scaffoldState.snackbarHostState.showSnackbar("Failed Add to Favorite")
                  }
                }
              }
            }) {
              Icon(
                if (viewModel.is_favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null
              )
            }
            IconButton(
              onClick = {
                openDialog = true
              }
            ) {
              Icon(
                painterResource(
                  id = if (viewModel.is_reminder) R.drawable.ic_baseline_notifications_24 else R.drawable.ic_baseline_notifications_none_24
                ),
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
    ) {
      if (!loaded) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          CircularProgressIndicator(
            modifier = Modifier
          )
        }
        LaunchedEffect(key1 = "load_data_detail") {
          launch {
            loaded = try {
              viewModel.load(id)
              true
            } catch (e: Exception) {
              scaffoldState.snackbarHostState.showSnackbar("Data Cannot Load")
              false
            }
          }
        }
      } else {
        val scrollState = rememberScrollState()
        Column(
          modifier = Modifier
            .verticalScroll(state = scrollState)
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Card() {
            Column(
              modifier = Modifier
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
              GlideImage(
                imageModel = viewModel.image,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 60),
                placeHolder = painterResource(id = R.drawable.ic_outline_image_24),
                error = painterResource(id = R.drawable.ic_outline_broken_image_24)
              )
              Text(
                text = viewModel.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier
                  .fillMaxWidth()
              )
            }
          }
          Card() {
            Column(
              modifier = Modifier
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = "Detail Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier
                  .fillMaxWidth()
              )
              Divider()
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Alternative Title :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.title_alt,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Episode :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.episode,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Status :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.status,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Aired :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.aired,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Premiered :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.premiered,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Source :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.source,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Producers :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.producer,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Studios :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.premiered,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Genres :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.genre,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Themes :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.theme,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Duration :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.duration,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
              Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Text(
                  text = "Rating :",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Left,
                )
                Text(
                  text = viewModel.rating,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Left,
                  maxLines = 2,
                  softWrap = true,
                  modifier = Modifier
                    .fillMaxWidth()
                )
              }
            }
          }
          Card() {
            Column(
              modifier = Modifier
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Text(
                text = "Synopsis",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier
                  .fillMaxWidth()
              )
              Divider()
              Text(
                text = viewModel.synopsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                modifier = Modifier
                  .fillMaxWidth()
              )
            }
          }
        }
      }
      if (openDialog) {
        if (viewModel.is_reminder) {
          AlertDialog(
            onDismissRequest = {
              openDialog = false
            },
            title = {
              Text(text = "Turn off Notification")
            },
            text = {
              Text(
                "Are you sure to turn off notification"
              )
            },
            confirmButton = {
              TextButton(
                onClick = {
                  openDialog = false
                  coroutineScope.launch {
                    try {
                      viewModel.del_reminder()
                      scaffoldState.snackbarHostState.showSnackbar("Successfully Turned off Notifications")
                    } catch (e: Exception) {
                      scaffoldState.snackbarHostState.showSnackbar("Failed Turned off Notifications")
                    }
                  }
                }
              ) {
                Text("Yes")
              }
            },
            dismissButton = {
              TextButton(
                onClick = {
                  openDialog = false
                }
              ) {
                Text("Cancel")
              }
            }
          )
        } else {
          AlertDialog(
            onDismissRequest = {
              openDialog = false
            },
            title = {
              Text(text = "Turn on Notification")
            },
            text = {
              Text(
                "Are you sure to turn on notification"
              )
            },
            confirmButton = {
              TextButton(
                onClick = {
                  openDialog = false
                  coroutineScope.launch {
                    try {
                      viewModel.add_reminder()
                      scaffoldState.snackbarHostState.showSnackbar("Successfully Turned on Notifications")
                    } catch (e: Exception) {
                      scaffoldState.snackbarHostState.showSnackbar("Failed Turned on Notifications")
                    }
                  }
                }
              ) {
                Text("Yes")
              }
            },
            dismissButton = {
              TextButton(
                onClick = {
                  openDialog = false
                }
              ) {
                Text("Cancel")
              }
            }
          )
        }
      }
    }
  }
}