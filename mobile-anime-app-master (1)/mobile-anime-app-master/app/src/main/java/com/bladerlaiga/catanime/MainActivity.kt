package com.bladerlaiga.catanime

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bladerlaiga.catanime.ui.theme.CataNimeTheme
import java.util.*

const val LOG_T = "[T]"

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CataNimeTheme {
        MainContent()
      }
    }
  }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainContent() {
  var splashed by remember { mutableStateOf(false) }
  if (splashed) {
    NavigatorContent()
  } else {
    SplashContent() {
      splashed = true
    }
  }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true,
  name = "Dark Mode"
)
@Composable
fun MainPreview() {
  CataNimeTheme {
  }
}
