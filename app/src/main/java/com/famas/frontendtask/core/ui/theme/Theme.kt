package com.famas.frontendtask.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.famas.frontendtask.core.util.TextColor.textPrimaryDarkColor
import com.famas.frontendtask.core.util.TextColor.textPrimaryLightColor
import com.famas.frontendtask.core.util.TextColor.textSecondaryDarkColor
import com.famas.frontendtask.core.util.TextColor.textSecondaryLightColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("ConflictingOnColor")
private val DarkGreenColorPalette = darkColors(
    primary = appGreenColor,
    primaryVariant = appGreenColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkPurpleColorPalette = darkColors(
    primary = appPurpleColor,
    primaryVariant = appPurpleColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkBlueColorPalette = darkColors(
    primary = appBlueColor,
    primaryVariant = appBlueColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkOrangeColorPalette = darkColors(
    primary = appOrangeColor,
    primaryVariant = appOrangeColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkRedColorPalette = darkColors(
    primary = appRedColor,
    primaryVariant = appRedColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkPinkColorPalette = darkColors(
    primary = appPinkColor,
    primaryVariant = appPinkColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

@SuppressLint("ConflictingOnColor")
private val DarkYellowColorPalette = darkColors(
    primary = appYellowColor,
    primaryVariant = appYellowColor,
    onPrimary = textPrimaryDarkColor,
    onSecondary = textSecondaryDarkColor,
    onBackground = cardBackgroundBlackDark,
    secondary = cardBackgroundBlackDark,
    background = scaffoldDarkBlackDark
)

// Light pallets
@SuppressLint("ConflictingOnColor")
private val LightGreenColorPalette = lightColors(
    primary = appGreenColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

@SuppressLint("ConflictingOnColor")
private val LightPurpleColorPalette = lightColors(
    primary = appPurpleColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

@SuppressLint("ConflictingOnColor")
private val LightBlueColorPalette = lightColors(
    primary = appBlueColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

@SuppressLint("ConflictingOnColor")
private val LightOrangeColorPalette = lightColors(
    primary = appOrangeColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

@SuppressLint("ConflictingOnColor")
private val LightRedColorPalette = lightColors(
    primary = appRedColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground

)

@SuppressLint("ConflictingOnColor")
private val LightPinkColorPalette = lightColors(
    primary = appPinkColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

@SuppressLint("ConflictingOnColor")
private val LightYellowColorPalette = lightColors(
    primary = appYellowColor,
    onPrimary = textPrimaryLightColor,
    onSecondary = textSecondaryLightColor,
    onBackground = appScaffold,
    secondary = appLightGreyColor,
    background = appBackground
)

enum class ColorPallet {
    RED, PURPLE, GREEN, ORANGE, BLUE, YELLOW, PINK
}

@ExperimentalAnimationApi
@Composable
fun FrontendTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPallet: ColorPallet = ColorPallet.BLUE,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val colors = when (colorPallet) {
        ColorPallet.GREEN -> if (darkTheme) DarkGreenColorPalette else LightGreenColorPalette
        ColorPallet.PURPLE -> if (darkTheme) DarkPurpleColorPalette else LightPurpleColorPalette
        ColorPallet.ORANGE -> if (darkTheme) DarkOrangeColorPalette else LightOrangeColorPalette
        ColorPallet.BLUE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
        ColorPallet.RED -> if (darkTheme) DarkRedColorPalette else LightRedColorPalette
        ColorPallet.YELLOW -> if (darkTheme) DarkYellowColorPalette else LightYellowColorPalette
        ColorPallet.PINK -> if (darkTheme) DarkPinkColorPalette else LightPinkColorPalette
    }.also {
        systemUiController.setStatusBarColor(it.surface)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}