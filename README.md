# Navigation with Animated Transitions Using Jetpack Compose

<img src="https://github.com/JohannBlake/adoptme/blob/main/images/navigation.gif" width="300" height="600">

This app demonstrates how to use animated transitions when navigating between screens when using Jetpack Compose. It also shows how to pass any type of data to another screen.

I have also written an article on Medium that covers the code in this app in much more detail. It touches on developing with Compose beyond what you read in the official Android docs. You can find the article at:<br>

https://medium.com/@johannblake/navigation-with-animated-transitions-using-jetpack-compose-daeb00d4fb45
<br>

This app does not use the Compose navigation framework due to its limitation of the data types that you can use when passing data to another screen. These are limited to the same subset you would normally use under the older View system when passing data to activities and fragments, i.e., the types restricted to bundles. Because Compose doesn't use activities or fragmenets (other than the startup activity), I personally never felt there was a need to restrict the app to these data types. You should be able to pass any data type.

Another limitation is that animated transitions are currently not supported when navigating to and from screens, although Google has mentioned in their docs that this feature is in the pipeline.

As a result, I created my own navigation framework. It is designed in such a way that you can apply animated transitions however you want and these animations are not controlled by the navigation framework. In fact, the navigation framework is so simple that it's only prupose is to manage a screen stack and provides some additional functions that make navigation easier. Referred to as the **Navigation Manager**, the navigation manager is the source of truth of your app's navigation history. Unlike the Compose navigation framework, there is no Navigation Host or any concept of a graph. You simply initialize the Navigation Manager with the types of screens your app will be using, optionally provide the type of class you want to use as the viewmodel for the screen and indicate which of the screens will act as your home screen. After that, it's just a matter of calling the Navigation Manager APIs to navigate to a screen, go back to a previous screen, or return to the home screen.

The code for the Navigation Manager is separate from the app's demo code and provided as an Android Library module under the **navigation** folder.

The data for the app is retrieved from an API I created on Wirespec:
[https://wirespec.dev/Wirespec/projects/apis/AdoptPets](https://wirespec.dev/Wirespec/projects/apis/AdoptPets)

It basically provides a list of 60 cats available for adoption. Wirespec is free. I developed it and own it. It's a great way to develop REST APIs quickly and easily with endpoints that can even match those on your production servers.

<br>

## App Usage

A navigation drawer is provided and contains a large list of menu items. Only the Home and Settings menu items have their own dedicated screens when you click on them. All the other menu items use the same screen, which I call a **dummy screen**. The purpose of the dummy screen is to show how you can re-use the same screen for different purposes.

It should be noted that you can only open up the navigation drawer while on the home screen. Whether you would want to limit it this way or allow it to be available under other conditions is something you can easily implement as explained in the Medium article.

Clicking on a grid item, takes you to the details screen for the item you have selected. Images of the cats are loaded dynamically. If you scroll to the bottom, there is a button that you can click on to adopt the pet. It takes you to a dummy screen.

The dummy screen has a button that lets you create another instance of a dummy screen. You can click on this and keep repeating it. At some point, you can then use the Back button and navigate back to the home screen to see how the state of each screen is maintained. There is also a button on the dummy screen that will take you directly to the home screen. If you click that, you end up clearing the navigation stack. At that point, if you click the Back button again, you'll exit the app.

<br>

## Navigation Manager API

1. Create a sealed class listing all the screens your app will show. It can be called whatever you want, but naming it *Screens* makes it obvious. It really just serves as an enum. Example:

```kotlin
sealed class Screens {
    object PetsList : Screens()
    object PetDetails : Screens()
    object Settings : Screens()
    object Dummy : Screens()
}
```

2. During your app's startup, use the **addScreens** function to add all the screens you want to use in your app. A good place to put this is in a class that inherits from Application. Optionally provide any viewmodels that each screen will use. Note, you are passing in a class reference to a viewmodel and not an instance. Navigation Manager will create the viewmodel for the screen when the screen needs to be displayed. Each screen is provided its own unique viewmodel and no two screens share the same view model. The viewmodels are removed when the user navigates back to a previous screen or navigates to the home screen. Mark one (and only one) of the screens as being the home screen by setting **isHomeScreen** to true. The order in which you specify the screens is not important:

```kotlin
NavigationManager.addScreens(
    mutableListOf(
        ScreenInfo(screen = Screens.PetsList, viewmodel = PetsListViewModel::class.java, isHomeScreen = true),
        ScreenInfo(screen = Screens.PetDetails, viewmodel = PetDetailsViewModel::class.java),
        ScreenInfo(screen = Screens.Dummy),
        ScreenInfo(screen = Screens.Settings, viewmodel = SettingsViewModel::class.java)
    )
)
```

3. Create a Composable that will act as a screen factory. See **ScreenFactoryUI.kt** for details. This is a UI composable, meaning that it will generate the actual screen. You would normally use this in a place such as a Scaffold's content, as the demo does. In essence, whenever you want to navigate to or from a screen, Navigation Manager will notifiy the factory causing it to recompose. The factory is responsible for iterating through all the screens currently in the navigation stack and recomposing all of them. It should be noted that this iteration does not mean every screen is actually recomposed. The Compose framework determines internally whether composables should be recomposed and this is often the result of parameters in the Composable function changing. Consult the Compose documentation on the specifics of how it determines when to recompose or not. The screen factory is also responsible for providing any animation transitions from one screen to another. In this demo app, a slide in/out from the left is used for all screens, but you can set up different animations for different screens. There is no animation used for the home screen when the app starts.

4. In your main activity, add code to detect the back button and notify the Navigation Manager whenever the user navigates backward. If the goBack function returns false, it means that there are no other screens to navigate back to and the app should exit:
   
   ```kotlin
   override fun onBackPressed() {  
       if (!NavigationManager.goBack())  
           super.onBackPressed()  
   }
   ```
   
   <br>

The following lists the APIs you can use from the Navigation Manager:

| function / property   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| addScreens            | ```fun addScreens(allScreens: List<ScreenInfo<*>>)```<br/><br/>Adds one or more screens that Navigation Manager will create when the user navigates to another screen. Screen instances are destroyed when the user navigates back to the previous screen.                                                                                                                                                                                                                                                                                                                                                                        |
| navigateTo            | ```fun navigateTo(screen: Any, screenData: Any? = null)```<br/><br/>Navigates to a screen. Specify the type of screen to navigate to and include any optional parameters you want to pass to the screen.                                                                                                                                                                                                                                                                                                                                                                                                                          |
| goBack                | ```fun goBack(): Boolean```<br/><br/>Navigates back to the previous screen. If the user is on the home screen when this is called, this function will return false, meaning that there are no other screens to navigate back to. The app should exit when false is returned.                                                                                                                                                                                                                                                                                                                                                      |
| navigateToHomeScreen  | ```fun navigateToHomeScreen()```<br/><br/>Navigates to the home screen. The navigation history is cleared. If the user hits the Back button at this point, they would exit the app.                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| getNavInfo            | ```fun getNavInfo(index: Int): NavigationInfo```<br/><br/>Returns navigation information for a specific item in the navigation stack. The index of the first item in the stack starts with zero and this will always be the home page. The last item in the stack is a placeholder screen and is not visible.<br/><br/>The property **onCloseScreen**is a LiveData observable that will get updated whenever the user navigates back to the previous screen. The value of the observable will be set to true to indicate to the observer to close the screen:<br/><br/>```navInfo?.onCloseScreen?.observeAsState(false)?.value``` |
| observeScreenChange   | ```fun observeScreenChange(callback: (screen: Any) -> Unit)```<br/><br/>Notifies subscribers whenever the Navigation Manager navigates to another screen or navigates back to a previous one. This should only be used by non-UI code to perform any necessary tasks whenever the screen changes.                                                                                                                                                                                                                                                                                                                                 |
| onScreenChange        | ```onScreenChange: LiveData<Int>```<br/><br/>The Navigation Manager uses this to notify the screen factory composable to update the screens . This is an observable with a value set to a random number between 0 and 1 million. A random number is used in order to force LiveData to update its value and notify the observer (which is the screen factory). The value itself has no meaning.<br/><br/>```NavigationManager.onScreenChange.observeAsState(0).value```                                                                                                                                                           |
| navStackCount         | ```navStackCount: Int```<br/><br/>The total number of screens on the navigation stack. This includes the hidden placeholder screen that is always placed at the end of the stack. There will always be at least two items on the stack - the first item will be the home screen and the last item will be the placeholder screen.                                                                                                                                                                                                                                                                                                 |
| totalScreensDisplayed | ```totalScreensDisplayed: Int```<br/><br/>The total number of screens on the navigation stack, minus one. This is essentially the same as navStackCount but doesn't include the placeholder screen. This is just a convenient way of knowing how many visible screens are currently being displayed.                                                                                                                                                                                                                                                                                                                              |