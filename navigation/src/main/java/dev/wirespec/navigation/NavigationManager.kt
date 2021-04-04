package dev.wirespec.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Manages the navigation to and from screens in an Android app. Although developed to work primarily with
 * apps that use Jetpack Compose, it can also be used to provided navigation for the older View based system.
 *
 * Navigation Manager is intended to replace Android's own Jetpack navigation system. It is light weight, lets
 * you pass any data type from one screen to another (including objects) and allows easy implementation of
 * animated transitions to and from other screens.
 *
 * Navigation Manager can create and manage the lifecycle of viewmodels for each screen and is the single source
 * of truth for which screen is currently being displayed and tracks the user's navigation path.
 *
 * As a singleton, Navigation Manager should be initialized in your app in a class that inherits from
 * Application, when the app starts up. It remains resident throughout the lifetime of your app and will
 * retain the state of a screen in the event of screen being recomposed or even if the activity hosting
 * the screen gets killed.
 */
class NavigationManager {
    companion object {
        private const val MaxRandomNumber = 1000000
        private lateinit var screens: List<ScreenInfo<*>>
        private val screenChangeObservers = mutableListOf<(Any) -> Unit>()
        private var navStack = mutableListOf<NavigationInfo>()
        private val _onScreenChange = MutableLiveData(0)

        private fun notifyScreenChange(screen: Any) {
            _onScreenChange.value = (0..MaxRandomNumber).random()
            notifyObserversOfScreenChange(screen)
        }

        private fun resetStackToHomeScreen() {
            navStack.clear()
            val screenInfo = screens.first { it.isHomeScreen }

            val navInfo = NavigationInfo(
                screen = screenInfo.screen,
                viewmodel = screenInfo.viewmodel?.newInstance() as ViewModel
            )

            navStack.add(navInfo)

            // Add a stack item at the end. It acts as a placeholder for the next screen. It needs
            // to be added in advance and composed when the previously added screen is also composed
            // in order for the animations to work.

            val nextScreen = NavigationInfo()
            addLiveDataToCloseScreen(nextScreen)
            navStack.add(nextScreen)
        }

        private fun addLiveDataToCloseScreen(navInfo: NavigationInfo) {
            val _onCloseScreen = MutableLiveData(false)
            val onCloseScreen: LiveData<Boolean> = _onCloseScreen
            navInfo.onCloseScreen = onCloseScreen
            navInfo._onCloseScreen = _onCloseScreen
        }

        private fun notifyObserversOfScreenChange(screen: Any) {
            val iterator = screenChangeObservers.iterator()

            while (iterator.hasNext()) {
                val observer = iterator.next()

                try {
                    observer(screen)
                } catch (e: Exception) {
                    iterator.remove()
                }
            }
        }

        /**
         * Indicates the total number of screens on the stack. This includes the hidden placeholder
         * screen that always appears last in the stack.
         */
        val navStackCount: Int
            get() {
                return navStack.size
            }

        /**
         * Indicates the total number of screens on the stack excluding the hidden placeholder
         * screen that always appears last in the stack.
         */
        val totalScreensDisplayed: Int
            get() {
                return navStack.size - 1
            }

        /**
         * Used to notify the screen factory that it needs to recompose the screens.
         * The value returned is a random number between 0 and one million. Using a
         * randomly generated value forces LiveData to update and notify any observers.
         */
        val onScreenChange: LiveData<Int> = _onScreenChange


        /**
         * Returns navigation information about the current screen.
         */
        val currentScreenNavInfo: NavigationInfo
            get() {
                return navStack[navStack.lastIndex - 1]
            }


        /**
         * Returns navigation information about the previous screen. Returns null
         * if there is no previous screen, which would be the case if the current
         * screen is the home screen.
         */
        val previousScreenNavInfo: NavigationInfo?
            get() {
                if (navStack.size < 3) {
                    return null
                }

                return navStack[navStack.lastIndex - 2]
            }

        /**
         * An observer that clients can register with to get notified whenever the screen changes.
         * Note: This is intended to be used by parts of an app that do not generate any UI.
         * @param callback Upon notification of a screen change, this will indicate the type
         * of screen that is being displayed. It doesn't indicate which screen but just the type.
         * It is possible that your app could have multiple Settings screens that are all based on the
         * same type. Even if the user navigates from the current screen to another one of the same
         * type, this parameter will only indicate the type.
         */
        fun observeScreenChange(callback: (screen: Any) -> Unit) {
            screenChangeObservers.add(callback)
        }

        /**
         * Adds all the screens that an app will use. This should only be called once in the app and
         * should be called when the app is initialized.
         *
         * @param allScreens A list of one or more screen definitions. One of the screens must have
         * its isHomeScreen property set to true to indicate that it is the home screen.
         */
        fun addScreens(allScreens: List<ScreenInfo<*>>) {
            screens = allScreens
            resetStackToHomeScreen()
        }

        /**
         * Navigates to the specified screen. Calling this function makes the
         * specified screen the last screen in the navigation stack although a
         * hidden placeholder screen is placed after it.
         * @param screen The type of screen that is being navigated to.
         * @param screenData Data that can be passed on to the destination screen.
         */
        fun navigateTo(screen: Any, screenData: Any? = null) {

            val stackScreen = navStack.last()
            val screenInfo = screens.first { it.screen == screen }

            stackScreen.apply {
                this.screen = screen
                this.screenData = screenData

                if (screenInfo.viewmodel != null) {
                    this.viewmodel = screenInfo.viewmodel.newInstance() as ViewModel
                }
            }

            // Add a hidden placeholder screen.
            val nextScreen = NavigationInfo()
            addLiveDataToCloseScreen(nextScreen)

            navStack.add(nextScreen)
            notifyScreenChange(screen)
        }

        /**
         * Navigates to the home screen. All previously screens are removed from
         * the navigation stack making the home screen the only screen on the stack
         * with the hidden placeholder screen added after it. Using the Back button
         * at this point normally should cause the app to exit.
         */
        fun navigateToHomeScreen() {
            if (navStack.size > 2) {
                navStack = navStack.filterIndexed { index, _ ->
                    (index == 0) || (index == navStack.lastIndex)
                }.toMutableList()
            }

            notifyScreenChange(navStack.first().screen!!)
        }

        /**
         * Navigates back to the previous screen. If the current screen's view model is controlled by
         * the navigation manager, a check will first be made to see if the view model implements the
         * navigateBackImmediately function. If it does, a call is first made to navigateBackImmediately. If
         * navigateBackImmediately returns true, the navigation manager will navigate to the previous screen (if one exists)
         * If navigateBackImmediately returns false, no navigation is made to the previous screen. If the current
         * screen needs to perform clean up work or prompt the user about something prior to navigating to the previous screen, the
         * current screen can then call the goBackImmediately function when it is ready to return to the previous screen.
         *
         * Once navigation to the previous screen is allowed, the current screen is removed from the navigation stack.
         * If the current screen is the home screen, then hitting the Back button should cause the app to exit.
         */
        fun goBack(): Boolean {

            if ((currentScreenNavInfo.viewmodel != null) && (currentScreenNavInfo.viewmodel is NavigationManagerHelper)) {
                val navHelper = currentScreenNavInfo.viewmodel as NavigationManagerHelper

                if (!navHelper.navigateBackImmediately()) {
                    return true
                }
            }

            return goBackImmediately()
        }


        /**
         * Navigates back to the previous screen immediately. No check is made to see if the current screen
         * requires a confirmation to go back. The current screen is removed from the navigation stack. If the current screen
         * is the home screen, then hitting the Back button should cause the app to exit.
         */
        fun goBackImmediately(): Boolean {
            if (navStack.size == 2) {
                resetStackToHomeScreen()
                return false
            }

            val lastIndex = navStack.lastIndex - 1
            val navInfo = navStack[lastIndex]
            navInfo._onCloseScreen?.value = true
            navStack.removeAt(lastIndex)

            val navInfoLastScreen = navStack[lastIndex - 1]
            notifyObserversOfScreenChange(navInfoLastScreen.screen!!)
            return true
        }

        /**
         * Returns navigation information about any item in the navigation stack.
         * @param index The first item in the stack starts with zero. Use the
         * totalScreensDisplayed property to get the last index.
         */
        fun getNavInfo(index: Int): NavigationInfo {
            return navStack[index]
        }
    }
}
