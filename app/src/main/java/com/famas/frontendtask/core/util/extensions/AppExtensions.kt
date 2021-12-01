package com.famas.frontendtask.core.util.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.famas.frontendtask.BaseApplication
import com.famas.frontendtask.R
import com.famas.frontendtask.core.data.local.shared_pref.SharedPrefUtils
import com.famas.frontendtask.core.ui.theme.shadowColor
import com.google.accompanist.pager.ExperimentalPagerApi
import okio.IOException

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.appShadow(
    color: Color? = null,
): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        drawColoredShadow(color ?: shadowColor)
    } else {
        shadow(
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
        )
            .background(color ?: shadowColor)

    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
fun getSharedPrefInstance(): SharedPrefUtils {
    return if (BaseApplication.sharedPrefUtils == null) {
        BaseApplication.sharedPrefUtils = SharedPrefUtils()
        BaseApplication.sharedPrefUtils!!
    } else {
        BaseApplication.sharedPrefUtils!!
    }
}

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val paintColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
        val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())

        this.drawIntoCanvas {
            val paint = Paint()
            paint.asFrameworkPaint().apply {
                this.color = paintColor
                this.setShadowLayer(
                    shadowRadius.toPx(),
                    offsetX.toPx(),
                    offsetY.toPx(),
                    shadowColor
                )
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                    this.strokeWidth = 2.0f
                }
                this.strokeCap = android.graphics.Paint.Cap.ROUND
                this.strokeJoin = android.graphics.Paint.Join.ROUND
            }
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    } else {
        shadow(
            elevation = 5.dp,
            shape = RoundedCornerShape(borderRadius),
        ).background(color = color)
    }
}
