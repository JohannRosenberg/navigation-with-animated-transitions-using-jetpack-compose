package dev.wirespec.navigation

/**
 * Used to define the type of screen to display.
 */
open class ScreenInfo<T>(
    /**
     * Identifies the type of screen. It is recommended to use objects based on a sealed class to create a quasi
     * enum, although using regular enums or even constants are also valid.
     */
    val screen: Any,

    /**
     * The viewmodel that should be associated with the screen. This is optional. A screen is not required to use a
     * viewmodel and can even manage its own viewmodel. If a viewmodel class is provided and the client navigates to
     * a screen, the Navigation Manager will create the viewmodel and remove it when the user navigates either backwards
     * or navigates to the home screen.
     */
    val viewmodel: Class<T>? = null,

    /**
     * Indicates that the screen is to be considered the home screen. Only one screen should be designated as the home
     * screen.
     */
    val isHomeScreen: Boolean = false
)