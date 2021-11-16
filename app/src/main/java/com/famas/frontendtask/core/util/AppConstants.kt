package com.famas.frontendtask.core.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object  AppConstants{
    const val appName = "ProApps"
}

object IntentKeys {
    const val DATA = "DATA"
    const val MODE = "MODE"
    const val APP_THEME = "APP_THEME"
    const val IS_DASHBOARD = "IS_DASHBOARD"
}

object style {
    var fontWeightBoldGlobal: FontWeight = FontWeight.Bold
    var fontWeightPrimaryGlobal: FontWeight = FontWeight.Normal
    var fontWeightSecondaryGlobal: FontWeight = FontWeight.Normal

}

object TextColor {
    val textPrimaryColor = Color(0xFF2E3033)
    val textSecondaryColor = Color(0xFF757575)
    //val textPrimaryLightColor = Color(0xFF212121)
    val textPrimaryDarkColor = Color(0xFFEEEEEE)
    //val textPrimaryDarkColor = Color(0xFFFFFFFF)
    val textPrimaryLightColor = Color(0xFFEEEEEE)
    val textSecondaryLightColor = Color(0xFF5A5C5E)
    val textSecondaryDarkColor = Color(0x8AFFFFFF)
}

object Size {
    val textBoldSizeGlobal = 17.0.sp
    val textPrimarySizeGlobal = 17.0.sp
    val textSecondarySizeGlobal = 14.0.sp
    val textExtraSize = 18.sp
    val textCaption = 12.sp
}

object Pref {
    const val myPreferences = "MyPreferences"
    const val PREF_MODE = "PREF_MODE"
    const val PREF_THEME_COLOR = "PREF_THEME_COLOR"
}


const val packageName = "com.iqonic.prokitjetpack"

object  Url {
    var settingDocumentation="https://wordpress.iqonic.design/docs/product/prokit-jetpack-compose/"
    var settingChangeLog="https://wordpress.iqonic.design/docs/product/prokit-jetpack-compose/updates/change-logs/"
    var settingRateUs="https://play.google.com/store/apps/details?id=$packageName"
}