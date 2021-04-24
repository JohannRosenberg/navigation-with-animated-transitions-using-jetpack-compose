package dev.wirespec.navigation

interface NavigationManagerHelper {
    fun onNavigateBack(): NavigateBackOptions
}

enum class NavigateBackOptions {
    /**
     * Causes the Navigation Manager to navigate to the previous screen without checking to see if the onNavigateBack
     * interface function is defined.
     */
    GoBackImmediately,

    /**
     * Same as GoBackImmediately but the navigation info for the current screen is cached so that it can be re-used
     * later on. The nav info only gets cached if it isn't in the cache already. In order to be cached, the id
     * parameter of the navigateTo function must be provided. If it is not provided, an exception is thrown. Note: The
     * cache is not the same thing as the navigation stack. The cache is a separate cache used to keep an instance of
     * the nav info when moving back. The nav info is removed from the navigation stack when navigating back.
     */
    GoBackImmediatelyAndCacheScreen,

    /**
     * Same as GoBackImmediately but the navigation info for the current screen is removed from the cache (if it exist
     * in the cache). In order to be removed from  the cache, the id parameter of the navigateTo function must be
     * provided. If it is not provided, an exception is thrown.
     */
    GoBackImmediatelyAndRemoveCachedScreen,

    /**
     * Cancels navigating to the previous screen.
     */
    Cancel
}