package com.famas.frontendtask.core.presentation.util

import androidx.compose.material.Colors
import androidx.compose.material.contentColorFor
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.famas.frontendtask.core.data.remote.responses.ThemeColorsResponse
import com.famas.frontendtask.core.presentation.activity_main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun provideColorPalette(
    viewModel: MainViewModel
): Colors {
    val sampleColors = remember { ThemeColorsResponse(0xFF4A6363, 0xFFF45B49) }
    val systemUiController = rememberSystemUiController()
    val colorsResponse = produceState(initialValue = sampleColors, producer = {
        this.value = viewModel.fetchColors().also {
            systemUiController.setStatusBarColor(Color(it.primary))
        }
    }).value

    return lightColors().copy(
        primary = Color(colorsResponse.primary),
        secondary = Color(colorsResponse.secondary),
        //onPrimary = contentColorFor(backgroundColor = Color(colorsResponse.primary)),
        onSecondary = contentColorFor(backgroundColor = Color(colorsResponse.secondary))
    )
}