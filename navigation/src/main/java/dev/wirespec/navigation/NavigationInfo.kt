package dev.wirespec.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Provides navigation information about a particular screen. Each screen stored in the Navigation Manager's
 * navigation stack is assigned an instance of NavigationInfo.
 */
data class NavigationInfo(

    /**
     * A unique id that can be used to identify this item in the navigation stack. This is optional.
     */
    var id: Any? = null,

    /**
     * The type of screen that is being displayed.
     */
    var screen: Any? = null,

    /**
     * Any data that is communicated to the screen from the previous screen. This is typically used
     * to pass data that the screen needs to display its data.
     */
    var screenData: Any? = null,

    /**
     * An optional viewmodel that is associated with the screen. Pass in a reference to a class that
     * inherits from ViewModel and not a reference to an actual instance. Navigation Manager will
     * create the viewmodel when the user navigates to the screen and will remove it from the navigation
     * stack when the user navigates to the previous screen.
     */
    var viewmodel: ViewModel? = null,

    /**
     * An optional title that the screen can use on its app bar if it has one.
     */
    var title: String? = null,


    internal var _onCloseScreen: MutableLiveData<Boolean>? = null,

    /**
     * Used to notify clients to close their screens. In Jetpack Compose, this typically means
     * preventing the screen from being displayed such as setting the visible property of AnimatedVisibility
     * to false or if no animations are used, just to prevent the composable from generating the UI.
     */
    var onCloseScreen: LiveData<Boolean>? = null
)
