package dev.wirespec.adoptme.ui.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.wirespec.adoptme.ui.nav.NavDrawerHandler
import dev.wirespec.navigation.NavigationManager

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MainHandler(modifier: Modifier = Modifier) {
    val vm: MainViewModel = viewModel()
    val scaffoldState = vm.scaffoldState

    var drawerGesturesEnabled by remember { mutableStateOf(true) }

    NavigationManager.observeScreenChange {
        drawerGesturesEnabled = (NavigationManager.totalScreensDisplayed == 1)
    }

    Main(scaffoldState, drawerGesturesEnabled = drawerGesturesEnabled, modifier = modifier)
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun Main(scaffoldState: ScaffoldState, drawerGesturesEnabled: Boolean, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        drawerGesturesEnabled = drawerGesturesEnabled,
        scaffoldState = scaffoldState,
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp,
        drawerContent = {
            NavDrawerHandler(scaffoldState = scaffoldState)
        },
        content = {
            ScreenFactoryHandler()
        }
    )
}

