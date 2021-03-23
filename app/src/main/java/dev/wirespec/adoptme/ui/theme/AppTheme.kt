package dev.wirespec.adoptme.ui.theme

import android.view.View
import android.view.WindowManager
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb

enum class ColorThemes {
    DefaultDark,
    DefaultLight
}

class AppTheme {
    companion object {
        lateinit var appColorTheme: ColorTheme

        @Composable
        fun setAppTheme(colorTheme: ColorThemes, content: @Composable() () -> Unit) {
            appColorTheme = when(colorTheme) {
                ColorThemes.DefaultDark -> DefaultDarkColorTheme()
                else -> DefaultLightColorTheme()
            }
            MaterialTheme(
                colors = appColorTheme.materialColors,
                typography = typography,
                shapes = shapes,
                content = content
            )

            setStatusBarColor()
        }

        @Composable
        fun setStatusBarColor() {
            val window = dev.wirespec.adoptme.App.context.currentActivity!!.getWindow()
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.statusBarColor = appColorTheme.statusBarBackground.toArgb()

            @Suppress("DEPRECATION")
            if (MaterialTheme.colors.surface.luminance() > 0.5f) {
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

            val decor = window.getDecorView();

            if (appColorTheme.materialColors.isLight) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }

        @Composable
        fun getButtonColors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                backgroundColor = appColorTheme.materialColors.secondary,
                contentColor = appColorTheme.materialColors.onSecondary
            )
        }
    }
}

open class ColorTheme(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,
    isLight: Boolean,
    statusBarBg: Color,
    drawerBg: Color,
    drawerContentColor: Color,
    drawerSelectedColor: Color,
    drawerItemSelectedBg: Color,
    drawerRightOverlayBg: Color
) {
    val materialColors = Colors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        onError,
        isLight
    )

    val statusBarBackground = statusBarBg
    val drawerBackground = drawerBg
    val drawerContent = drawerContentColor
    val drawerSelected = drawerSelectedColor
    val drawerItemSelectedBackground = drawerItemSelectedBg
    val drawerRightOverlayBackground = drawerRightOverlayBg
}

 data class DefaultLightColorTheme(
     val primary: Color = MaterialColors.gray900,
     val primaryVariant: Color = Color(0xFF3700B3),
     val secondary: Color = AppColors.turquoise,
     val secondaryVariant: Color = secondary,
     val background: Color = Color(0xFF121212),
     val surface: Color = MaterialColors.white,
     val error: Color = Color(0xFFCF6679),
     val onPrimary: Color = MaterialColors.yellow100,
     val onSecondary: Color = Color.White,
     val onBackground: Color = MaterialColors.gray900,
     val onSurface: Color = MaterialColors.gray900,
     val onError: Color = Color.Black,
     val isLight: Boolean = false,
     val statusBarBg: Color = AppColors.turquoise,
     val drawerBg: Color = AppColors.darkGray,
     val drawerContentColor: Color = MaterialColors.gray900,
     val drawerSelectedColor: Color = AppColors.darkTurquoise,
     val drawerItemSelectedBg: Color = MaterialColors.blue700,
     val drawerRightOverlayBg: Color = Color(0xFFbcfaff)
 ): ColorTheme(
     primary,
     primaryVariant,
     secondary,
     secondaryVariant,
     background,
     surface,
     error,
     onPrimary,
     onSecondary,
     onBackground,
     onSurface,
     onError,
     isLight,
     statusBarBg,
     drawerBg,
     drawerContentColor,
     drawerSelectedColor,
     drawerItemSelectedBg,
     drawerRightOverlayBg
 )

data class DefaultDarkColorTheme(
    val primary: Color = Color(0xFFBB86FC),
    val primaryVariant: Color = Color(0xFF3700B3),
    val secondary: Color = Color(0xFF03DAC6),
    val secondaryVariant: Color = secondary,
    val background: Color = Color(0xFF121212),
    val surface: Color = Color(0xff0f1014),
    val error: Color = Color(0xFFCF6679),
    val onPrimary: Color = Color.Black,
    val onSecondary: Color = Color.Black,
    val onBackground: Color = Color.White,
    val onSurface: Color = Color.White,
    val onError: Color = Color.Black,
    val isLight: Boolean = false,
    val statusBarBg: Color = Color(0xff161617),
    val drawerBg: Color = Color(0xff161617),
    val drawerContentColor: Color = MaterialColors.white,
    val drawerSelectedColor: Color = AppColors.turquoise,
    val drawerItemSelectedBg: Color = MaterialColors.blueGray600,
    val drawerRightOverlayBg: Color = Color(0xff9b82aa)
): ColorTheme(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    isLight,
    statusBarBg,
    drawerBg,
    drawerContentColor,
    drawerSelectedColor,
    drawerItemSelectedBg,
    drawerRightOverlayBg
)
