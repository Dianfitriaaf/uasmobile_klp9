package com.bladerlaiga.catanime

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bladerlaiga.catanime.ui.theme.CataNimeTheme
import kotlinx.coroutines.delay

@Composable
fun SplashContent(onFinish: () -> Unit) {
  Surface(color = MaterialTheme.colors.primary) {
    val scale = remember {
      Animatable(0f)
    }
    LaunchedEffect(key1 = "splash") {
      scale.animateTo(
        targetValue = 0.2f,
        animationSpec = tween(
          durationMillis = 800,
          easing = {
            OvershootInterpolator(4f).getInterpolation(it)
          })
      )
      delay(2000L)
      onFinish()
    }
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.fillMaxSize()
    ) {
      Image(
        painter = painterResource(id = R.drawable.proto_v2),
        contentDescription = "Logo",
        modifier = Modifier.scale(scale.value)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
  CataNimeTheme {
    SplashContent() { }
  }
}