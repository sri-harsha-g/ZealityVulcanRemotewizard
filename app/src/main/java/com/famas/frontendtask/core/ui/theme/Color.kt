package com.famas.frontendtask.core.ui.theme

import androidx.compose.ui.graphics.Color
import com.famas.frontendtask.core.util.Pref
import com.famas.frontendtask.core.util.extensions.getSharedPrefInstance

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Orange700 = Color(0xFFF45B49)
val Orange100 = Color(0xFFFFCCBC)

val PrimaryBlueGreen = Color(0xff1A434E)
val SecBlueGreen = Color(0xFF3A4564)
val CBlueGreen = Color(0xFF3A4564)

//val ghost_white = Color(0xffF3F3F3)

// Theme Color
val appGreenColor = Color(0xFF4AA96C)
val appBlueColor = Color(0xFF0A79DF)
val appPurpleColor = Color(0xFF9949d6)
val appOrangeColor = Color(0xFFFF4C29)
val appRedColor = Color(0xFFB61919)
val appYellowColor = Color(0xFFFA9905)
val appPinkColor = Color(0xFFE93B81)

//val appScaffold = Color(0xFFF3F3F3)
val appScaffold = Color(0xFFEEEEEE)
val GrayColor = Color(0xFFF4F4F7)
val appBackground = Color(0xFFF3F3F3)
val appIconColor = Color(0xFF8E8F91)
val appLightGreyColor = Color(0xFFFAFAFF)

// Dark color
val cardBackgroundBlackDark = Color(0xFF1D1D1D)
val scaffoldDarkBlackDark = Color(0xFF000000)
val appBarDarkBlackDark = Color(0xFF282828)

val iconTint1= Color(0xFF8998FE)
val iconTint2= Color(0xFFFF9781)
val iconTint3= Color(0xFF0A79DF)

val appShadowColor= Color(0x1F000000)
val appDarkShadowColor= Color(0x1FFFFFFF)

val shadowColor = if (getSharedPrefInstance().getBooleanValue(Pref.PREF_MODE)) appDarkShadowColor else appShadowColor
