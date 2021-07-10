package dev.wirespec.adoptme.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.screens.dummy.DummyHandler
import dev.wirespec.adoptme.ui.screens.petdetails.PetDetailsHandler
import dev.wirespec.adoptme.ui.screens.petslist.PetsListHandler
import dev.wirespec.adoptme.ui.screens.settings.SettingsHandler
import dev.wirespec.navigation.NavigationInfo
import dev.wirespec.navigation.NavigationManager


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ScreenFactoryHandler() {
    NavigationManager.onScreenChange.observeAsState(0).value

    val lastIndex = NavigationManager.navStackCount - 1

    for (i in 0..lastIndex) {
        val navInfo = NavigationManager.getNavInfo(i)
        ScreenFactory(isVisible = i < lastIndex, navInfo = navInfo)
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ScreenFactory(isVisible: Boolean, navInfo: NavigationInfo?, modifier: Modifier = Modifier) {

    var closeScreen = navInfo?.onCloseScreen?.observeAsState(false)?.value

    if (closeScreen == null)
        closeScreen = false

    if (navInfo?.screen == Screens.PetsList) {
        PetsListHandler(navInfo = navInfo, screenIsClosing = closeScreen)
        return
    }

    /**
     * IMPORTANT: It is strongly recommended that your screen's handler (PetDetailsHandler, DummyHandler,
     * SettingsHandler as used below) contain a parameter that is used to indicate when the screen is closing.
     * When animation in Compose is executed, the screen gets recomposed even when the screen is being
     * animated to exit. This means that any code in your screen handler will get executed when it closes.
     * If there is any code in your handler that should only be executed when the screen is being shown
     * (and not when it's removed), you should test to see if the screenIsClosing is set to true and not execute
     * the code in your handler.
     */

    AnimatedVisibility(
        visible = isVisible && !closeScreen,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        if (navInfo?.screen != null) {
            when (navInfo.screen) {
                Screens.PetDetails -> PetDetailsHandler(navInfo, screenIsClosing = closeScreen)
                Screens.Dummy -> DummyHandler(navInfo, screenIsClosing = closeScreen)
                Screens.Settings -> SettingsHandler(navInfo, screenIsClosing = closeScreen)
            }
        } else {
            Surface(modifier = modifier.fillMaxSize()) {}
        }
    }
}
