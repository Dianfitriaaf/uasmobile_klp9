package com.bladerlaiga.catanime.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*

enum class ScaleAndAlphaTransitionState { From, To }

data class ScaleAndAlphaArgs(
  val fromScale: Float,
  val toScale: Float,
  val fromAlpha: Float,
  val toAlpha: Float,
)

@Composable
fun rememberTransitionScaleAndAlphaState(initial: ScaleAndAlphaTransitionState = ScaleAndAlphaTransitionState.From): MutableTransitionState<ScaleAndAlphaTransitionState> {
  val state = remember {
    MutableTransitionState(initialState = initial).apply {
      if (currentState == ScaleAndAlphaTransitionState.From) {
        targetState = ScaleAndAlphaTransitionState.To
      } else {
        targetState = ScaleAndAlphaTransitionState.From
      }
    }
  }
  return state
}


@Composable
fun TransitionScaleAndAlpha(
  args: ScaleAndAlphaArgs,
  animation: FiniteAnimationSpec<Float>,
  transitionState: MutableTransitionState<ScaleAndAlphaTransitionState> = rememberTransitionScaleAndAlphaState()
): Pair<Float, Float> {
  val transition = updateTransition(transitionState, label = "")
  val alpha by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
    when (state) {
      ScaleAndAlphaTransitionState.From -> args.fromAlpha
      ScaleAndAlphaTransitionState.To -> args.toAlpha
    }
  }
  val scale by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
    when (state) {
      ScaleAndAlphaTransitionState.From -> args.fromScale
      ScaleAndAlphaTransitionState.To -> args.toScale
    }
  }
  return Pair(alpha, scale)
}

@Composable
fun LazyListState.calculateDelayAndEasing(index: Int, columnCount: Int): Easing {
  val row = index / columnCount
  val firstVisibleRow = firstVisibleItemIndex
  val visibleRows = layoutInfo.visibleItemsInfo.count()
  val scrollingToBottom = firstVisibleRow < row
  val isFirstLoad = visibleRows == 0
  return if (scrollingToBottom || isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing
}
