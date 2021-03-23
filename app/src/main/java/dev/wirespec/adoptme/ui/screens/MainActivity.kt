package dev.wirespec.adoptme.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import dev.wirespec.adoptme.ui.screens.main.MainHandler
import dev.wirespec.adoptme.ui.theme.AppTheme.Companion.setAppTheme
import dev.wirespec.adoptme.ui.theme.ColorThemes
import dev.wirespec.navigation.NavigationManager

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            setAppTheme(ColorThemes.DefaultLight) {
                MainHandler()
            }
        }
    }

    override fun onBackPressed() {
        if (!NavigationManager.goBack())
            super.onBackPressed()
    }
}

