package dev.wirespec.adoptme

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.screens.petdetails.PetDetailsViewModel
import dev.wirespec.adoptme.ui.screens.petslist.PetsListViewModel
import dev.wirespec.adoptme.ui.screens.settings.SettingsViewModel
import dev.wirespec.navigation.NavigationManager
import dev.wirespec.navigation.ScreenInfo


class App : Application() {

    private val activityLifecycleTracker: AppLifecycleTracker = AppLifecycleTracker()

    override fun onCreate() {
        super.onCreate()
        context = this

        registerActivityLifecycleCallbacks(activityLifecycleTracker)

        NavigationManager.addScreens(
            mutableListOf(
                ScreenInfo(screen = Screens.PetsList, viewmodel = PetsListViewModel::class.java, isHomeScreen = true),
                ScreenInfo(screen = Screens.PetDetails, viewmodel = PetDetailsViewModel::class.java),
                ScreenInfo(screen = Screens.Dummy),
                ScreenInfo(screen = Screens.Settings, viewmodel = SettingsViewModel::class.java)
            )
        )
    }

    companion object {
        lateinit var context: App
    }


    // Returns the current activity.
    var currentActivity: Activity?
        get() = activityLifecycleTracker.currentActivity
        private set(value) {}

    /**
     * Callbacks for handling the lifecycle of activities.
     */
    class AppLifecycleTracker : ActivityLifecycleCallbacks {

        private var currentAct: Activity? = null

        var currentActivity: Activity?
            get() = currentAct
            private set(value) {}

        override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            currentAct = activity
        }

        override fun onActivityPaused(p0: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            if ((currentAct != null) && (activity == currentAct))
                currentAct = null
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        }

        override fun onActivityDestroyed(p0: Activity) {
        }
    }
}
