package com.example.schalendar2

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object LocaleManager {

    fun setLocale(resources: Resources, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            resources.updateConfiguration(configuration, null)
        }
    }

    // You can call this method to change the locale in your Activity or Fragment
    // For example, com.example.schalendar2.LocaleManager.setLocale(resources, "fr") will change the locale to French
}