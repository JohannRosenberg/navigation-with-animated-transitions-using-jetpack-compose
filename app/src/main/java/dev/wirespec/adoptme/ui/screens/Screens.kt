package dev.wirespec.adoptme.ui

/**
 * Lists all the screens that will be displayed in the app.
 */
sealed class Screens {
    object PetsList : Screens()
    object PetDetails : Screens()
    object Settings : Screens()
    object Dummy : Screens()
}
