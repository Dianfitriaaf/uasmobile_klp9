package com.bladerlaiga.catanime

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bladerlaiga.catanime.models.EditViewModel
import com.bladerlaiga.catanime.ui.theme.Black200
import com.bladerlaiga.catanime.ui.theme.Black600
import com.bladerlaiga.catanime.ui.theme.CataNimeTheme
import kotlinx.coroutines.launch

@Composable
fun EditContent() {
  val viewModel = viewModel<EditViewModel>()
  val navigator = Navigator()
  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.TakePicturePreview()
  ) {
    viewModel.bitmap = it
  }
  Surface(
    modifier = Modifier,
    color = MaterialTheme.colors.background
  ) {
    Scaffold(
      scaffoldState = scaffoldState,
      modifier = Modifier,
      topBar = {
        TopAppBar(
          title = {
            Text(text = "Add Anime")
          }
        )
      }
    ) {
      val scrollState = rememberScrollState()
      Surface(
        modifier = Modifier.verticalScroll(state = scrollState)
      ) {
        Column(
          modifier = Modifier.padding(16.dp)
        ) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.surface
          ) {
            Column(
              modifier = Modifier.padding(16.dp)
            ) {
              if (viewModel.bitmap == null) {
                Image(
                  painter = painterResource(id = R.drawable.ic_outline_camera_alt_24),
                  contentDescription = null,
                  modifier = Modifier
                    .size(152.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = if (isSystemInDarkTheme()) Black600 else Black200)
                    .scale(0.3F)
                    .clickable {
                      launcher.launch()
                    }
                )
              } else {
                Image(
                  bitmap = viewModel.bitmap!!.asImageBitmap(),
                  contentDescription = null,
                  modifier = Modifier
                    .size(152.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                      launcher.launch()
                    }
                )
              }
              Spacer(modifier = Modifier.size(Dp.Infinity, 16.dp))
              TextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Title") },
                placeholder = { Text("") },
                modifier = Modifier.fillMaxWidth()
              )
            }
          }
          Spacer(modifier = Modifier.size(height = 16.dp, width = Dp.Infinity))
          Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.surface
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "Detail Information",
                fontSize = 18.sp,
                modifier = Modifier
              )
              Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
              TextField(
                value = viewModel.alt_name,
                onValueChange = { viewModel.alt_name = it },
                label = { Text("Alternative Name") },
                placeholder = { Text("") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = if (viewModel.episode == 0) "" else viewModel.episode.toString(),
                onValueChange = { viewModel.episode = it.toInt() },
                label = { Text("Episode") },
                placeholder = { Text("24") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.status,
                onValueChange = { viewModel.status = it },
                label = { Text("Status") },
                placeholder = { Text("Finished Airing") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.aired,
                onValueChange = { viewModel.aired = it },
                label = { Text("Aired") },
                placeholder = { Text("Apr 8, 2012 to Sep 22, 2012") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.premiered,
                onValueChange = { viewModel.premiered = it },
                label = { Text("Premiered") },
                placeholder = { Text("Spring 2012") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.source,
                onValueChange = { viewModel.source = it },
                label = { Text("Source") },
                placeholder = { Text("Manga") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.producer,
                onValueChange = { viewModel.producer = it },
                label = { Text("Producers") },
                placeholder = { Text("Bandai Visual, Lantis") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.studios,
                onValueChange = { viewModel.studios = it },
                label = { Text("Studios") },
                placeholder = { Text("Production I.G.") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.genre,
                onValueChange = { viewModel.genre = it },
                label = { Text("Genre") },
                placeholder = { Text("Comedy, Sports") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.theme,
                onValueChange = { viewModel.theme = it },
                label = { Text("Theme") },
                placeholder = { Text("School") },
                modifier = Modifier.fillMaxWidth()
              )
              Spacer(modifier = Modifier.size(height = 8.dp, width = Dp.Infinity))
              TextField(
                value = viewModel.duration,
                onValueChange = { viewModel.duration = it },
                label = { Text("Duration") },
                placeholder = { Text("24 min. per ep.") },
                modifier = Modifier.fillMaxWidth()
              )
            }
          }
          Spacer(modifier = Modifier.size(height = 16.dp, width = Dp.Infinity))
          Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.surface
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "Synopsis",
                fontSize = 18.sp,
                modifier = Modifier
              )
              Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
              TextField(
                value = viewModel.synopsis,
                onValueChange = { viewModel.synopsis = it },
                label = { Text("Synopsis ...") },
                placeholder = { Text("") },
                modifier = Modifier.fillMaxWidth()
              )
            }
          }
          Spacer(modifier = Modifier.size(height = 24.dp, width = Dp.Infinity))
          Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
              coroutineScope.launch {
                viewModel.save()
                scaffoldState.snackbarHostState
                  .showSnackbar("Save Success", "s")
                navigator.back()
              }
            }
          ) {
            Text(text = "Save")
          }
        }
      }
    }
  }
}

@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true,
  name = "Dark Mode"
)
@Preview(showBackground = true)
@Composable
fun EditPreview() {
  CataNimeTheme {
    EditContent()
  }
}