package com.famas.frontendtask.core.ui.theme

import androidx.compose.ui.graphics.Color
import com.famas.frontendtask.core.util.Pref
import com.famas.frontendtask.core.util.extensions.getSharedPrefInstance

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

val progress1 = Color(0xFF7dd88e)
val progress2 = Color(0xFFfb9a50)
val progress3 = Color(0xFFcc4344)
val progress4 = Color(0xFF90427a)
val progress5 = Color(0xFF1dd1fa)

val appShadowColor= Color(0x1F000000)
val appDarkShadowColor= Color(0x1FFFFFFF)

val shadowColor = if (getSharedPrefInstance().getBooleanValue(Pref.PREF_MODE)) appDarkShadowColor else appShadowColor
