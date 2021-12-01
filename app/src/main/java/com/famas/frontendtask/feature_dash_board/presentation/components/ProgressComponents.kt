package com.famas.frontendtask.feature_dash_board.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IndicatorRow(modifier: Modifier = Modifier, color: Color, indicatorName: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(SpaceMedium)
                .background(color)
        )
        Spacer(modifier = Modifier.width(SpaceSmall))
        Text(
            text = indicatorName,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ProgressPie(
    modifier: Modifier = Modifier,
    values: List<ProgressValues>,
    style: ProgressPieStyle = ProgressPieStyle.Stroke
) {
    val total = remember {
        values.map { it.value }.sum()
    }

    val animatableSweepAngles = remember {
        values.map { Animatable(0f) }
    }

    var startAngle = remember { 0f }

    BoxWithConstraints(modifier = modifier.size(100.dp)) {
        val width = maxWidth
        Canvas(modifier = Modifier.fillMaxSize()) {
            values.forEachIndexed { index, prValue ->
                drawArc(
                    color = prValue.color,
                    startAngle = startAngle,
                    sweepAngle = animatableSweepAngles[index].value,
                    useCenter = style == ProgressPieStyle.Full,
                    style = when(style) {
                        ProgressPieStyle.Stroke -> Stroke(width = width.times(0.20f).value, cap = StrokeCap.Butt)
                        ProgressPieStyle.Full -> Fill
                    })
                val sweep = (prValue.value.div(total) * 360f)
                startAngle += sweep
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        animatableSweepAngles.forEachIndexed { index, animatable ->
            launch {
                val sweep = (values[index].value.div(total) * 360f)
                animatable.animateTo(sweep, animationSpec = tween(durationMillis = 1000))
            }
        }
    })
}

data class ProgressValues(
    val value: Float,
    val color: Color
)

enum class ProgressPieStyle { Full, Stroke }