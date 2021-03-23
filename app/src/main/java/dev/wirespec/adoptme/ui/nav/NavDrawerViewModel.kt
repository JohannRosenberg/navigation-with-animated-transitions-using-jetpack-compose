package dev.wirespec.adoptme.ui.nav

import androidx.compose.foundation.ScrollState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.navigation.NavigationManager

class NavDrawerViewModel : ViewModel() {

    var navDrawerScrollState: ScrollState = ScrollState(0)

    private val _currentMenuId = MutableLiveData(NavMenuConstants.MenuHome)
    val currentMenuId: LiveData<String> = _currentMenuId

    init {
        NavigationManager.observeScreenChange {
            if  (NavigationManager.totalScreensDisplayed == 1) {
                _currentMenuId.value = NavMenuConstants.MenuHome
            }
        }
    }

    fun onNavItemClick(menuId: String, screen: Screens, screenData: Any? = null) {
        _currentMenuId.value = menuId

        if (screen == Screens.PetsList) {
            NavigationManager.navigateToHomeScreen()
        } else {
            NavigationManager.navigateTo(screen = screen,screenData = screenData)
        }
    }
}

