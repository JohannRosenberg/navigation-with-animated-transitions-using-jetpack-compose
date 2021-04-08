package dev.wirespec.adoptme.ui.screens.dummy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.wirespec.adoptme.App
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.screens.ScreenGlobals
import dev.wirespec.adoptme.ui.theme.AppColors
import dev.wirespec.adoptme.ui.theme.AppTheme
import dev.wirespec.navigation.NavigationInfo
import dev.wirespec.navigation.NavigationManager


@ExperimentalMaterialApi
@Composable
fun DummyHandler(navInfo: NavigationInfo, modifier: Modifier = Modifier) {

    val screenText = navInfo.screenData as String

    Dummy(
        screenText,
        modifier = modifier,
        onBackButtonClick = {
            NavigationManager.goBack()
        })
}

@ExperimentalMaterialApi
@Composable
fun Dummy(
    screenText: String,
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
                Text(screenText, color = AppTheme.appColorTheme.materialColors.secondary)
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
        ) {
            Text(
                "Screen id...${(0..1000).random()}",
                fontSize = 15.sp,
                modifier = modifier.padding(bottom = 10.dp)
            )

            Button(
                modifier = modifier.padding(bottom = 10.dp),
                colors = AppTheme.getButtonColors(),
                elevation = ButtonDefaults.elevation(5.dp),
                onClick = {
                    NavigationManager.navigateTo(screen = Screens.Dummy, screenData = "Dummy Screen")
                }) {
                Text(
                    text = "Navigate to another dummy screen",
                    modifier = modifier.padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 7.dp)
                )
            }

            Button(
                modifier = modifier.padding(bottom = 10.dp),
                colors = AppTheme.getButtonColors(),
                elevation = ButtonDefaults.elevation(5.dp),
                onClick = {
                    NavigationManager.navigateToHomeScreen()
                }) {
                Text(
                    text = "Go to home screen",
                    modifier = modifier.padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 7.dp)
                )
            }

            Button(
                modifier = modifier.padding(bottom = 10.dp),
                colors = AppTheme.getButtonColors(),
                elevation = ButtonDefaults.elevation(5.dp),
                onClick = {
                    App.context.currentActivity?.finish()
                }) {
                Text(
                    text = "Terminate activity",
                    modifier = modifier.padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 7.dp)
                )
            }
        }
    }
}