package com.famas.frontendtask.core.util.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import com.famas.frontendtask.R
import com.famas.frontendtask.core.util.Size
import com.famas.frontendtask.core.util.style

// Primary text style method
@Composable
fun primaryTextStyle(
    color: Color = contentColorFor(backgroundColor = MaterialTheme.colors.surface),
    fontSize: TextUnit = Size.textPrimarySizeGlobal,
    fontWeight: FontWeight? = style.fontWeightPrimaryGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    fontFamily: FontFamily? = FontFamily(Font(R.font.dmsansregular)),
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    alpha: Float? = null
): TextStyle {
    return TextStyle(
        color = alpha?.let { color.copy(it) } ?: color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        background = background,
        textDecoration = textDecoration,
        textAlign = textAlign,
        textDirection = textDirection,
        lineHeight = lineHeight,
    )
}

// Bold text style method

@Composable
fun boldTextStyle(
    color: Color = contentColorFor(backgroundColor = MaterialTheme.colors.surface),
    fontSize: TextUnit = Size.textBoldSizeGlobal,
    fontWeight: FontWeight? = style.fontWeightBoldGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    fontFamily: FontFamily? = FontFamily(Font(R.font.dmsansbold)),
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    alpha: Float? = null
): TextStyle {
    return TextStyle(
        color = alpha?.let { color.copy(it) } ?: color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        background = background,
        textDecoration = textDecoration,
        textAlign = textAlign,
        textDirection = textDirection,
        lineHeight = lineHeight,
    )
}

/* Secondary text style method */
@Composable
fun secondaryTextStyle(
    color: Color = contentColorFor(backgroundColor = MaterialTheme.colors.surface),
    fontSize: TextUnit = Size.textSecondarySizeGlobal,
    fontWeight: FontWeight? = style.fontWeightSecondaryGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    fontFamily: FontFamily? = FontFamily(Font(R.font.dmsansregular)),
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    alpha: Float? = null
): TextStyle {
    return TextStyle(
        color = alpha?.let { color.copy(it) } ?: color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        background = background,
        textDecoration = textDecoration,
        textAlign = textAlign,
        textDirection = textDirection,
        lineHeight = lineHeight,
    )
}