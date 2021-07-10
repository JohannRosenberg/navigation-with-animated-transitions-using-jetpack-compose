package dev.wirespec.adoptme.ui.screens.settings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.wirespec.adoptme.ui.screens.ScreenGlobals
import dev.wirespec.adoptme.ui.theme.AppColors
import dev.wirespec.adoptme.ui.theme.AppTheme
import dev.wirespec.navigation.NavigationInfo
import dev.wirespec.navigation.NavigationManager

@ExperimentalMaterialApi
@Composable
fun SettingsHandler(navInfo: NavigationInfo, modifier: Modifier = Modifier, screenIsClosing: Boolean = false) {

    val vm = navInfo.viewmodel as SettingsViewModel
    Settings(
        scrollState = vm.scrollState,
        modifier = modifier,
        onBackButtonClick = {
            NavigationManager.goBack()
        })
}

@ExperimentalMaterialApi
@Composable
fun Settings(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.appColorTheme.materialColors.surface)
    ) {

        TopAppBar(
            modifier = Modifier.height(ScreenGlobals.DefaultToolbarHeight),
            elevation = 0.dp,
            title = {
                Text("Settings", color = AppTheme.appColorTheme.materialColors.secondary)
            },
            navigationIcon = {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        modifier = modifier
                            .requiredWidth(ScreenGlobals.ToolbarBackButtonIconSize)
                            .requiredHeight(ScreenGlobals.ToolbarBackButtonIconSize),
                        tint = AppColors.turquoise,
                        imageVector = Icons.Filled.ArrowLeft,
                        contentDescription = ""
                    )
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(10.dp)
                .fillMaxSize()
                .background(AppColors.whiteAlpha)
                .verticalScroll(scrollState)
        ) {
            Text(
                "Settings stuff goes here...",
                fontSize = 20.sp
            )
        }
    }
}