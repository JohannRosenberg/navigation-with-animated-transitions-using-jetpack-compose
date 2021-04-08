package dev.wirespec.adoptme.ui.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.wirespec.adoptme.R
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.theme.AppTheme
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NavDrawerHandler(scaffoldState: ScaffoldState, modifier: Modifier = Modifier) {
    val vm: NavDrawerViewModel = viewModel()
    val currentMenuId = vm.currentMenuId.observeAsState(NavMenuConstants.MenuHome)

    val scrollState = vm.navDrawerScrollState
    val coroutineScope = rememberCoroutineScope()

    NavDrawer(
        currentMenuId.value,
        scrollState,
        onNavItemClick = { menuId, screen, screenData ->
            coroutineScope.launch {
                scaffoldState.drawerState.close()
                vm.onNavItemClick(menuId, screen, screenData)
            }
        },
        modifier = modifier
    )
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NavDrawer(
    currentMenuId: String,
    navDrawerScrollState: ScrollState,
    onNavItemClick: (menuId: String, screen: Screens, screenData: Any?) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Transparent,
        contentColor = AppTheme.appColorTheme.drawerContent
    ) {
        Row(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .background(AppTheme.appColorTheme.materialColors.surface)
                    .requiredWidth(200.dp)
                    .fillMaxHeight()
                    .padding(20.dp)
                    .verticalScroll(navDrawerScrollState)
            ) {
                NavMenuItem(
                    menuId = NavMenuConstants.MenuHome,
                    icon = Icons.Filled.Home,
                    title = "Home",
                    screen = Screens.PetsList,
                    selected = currentMenuId == NavMenuConstants.MenuHome,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuSettings,
                    icon = Icons.Filled.Settings,
                    title = "Settings",
                    screen = Screens.Settings,
                    selected = currentMenuId == NavMenuConstants.MenuSettings,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyAccount,
                    icon = Icons.Filled.AccountCircle,
                    title = "Account",
                    screen = Screens.Dummy,
                    dstArgs = "Account data goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyAccount,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyFavorites,
                    icon = Icons.Filled.Favorite,
                    title = "Favorites",
                    screen = Screens.Dummy,
                    dstArgs = "Favorites goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyFavorites,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyExplore,
                    icon = Icons.Filled.Explore,
                    title = "Explore",
                    screen = Screens.Dummy,
                    dstArgs = "Explore stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyExplore,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyFeedback,
                    icon = Icons.Filled.Feedback,
                    title = "Feedback",
                    screen = Screens.Dummy,
                    dstArgs = "Feedback stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyFeedback,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyRate,
                    icon = Icons.Filled.Grade,
                    title = "Rate",
                    screen = Screens.Dummy,
                    dstArgs = "Rating stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyRate,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyHelp,
                    icon = Icons.Filled.Help,
                    title = "Help",
                    screen = Screens.Dummy,
                    dstArgs = "Help stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyHelp,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyPrivacy,
                    icon = Icons.Filled.Https,
                    title = "Privacy",
                    screen = Screens.Dummy,
                    dstArgs = "Privacy stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyPrivacy,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyGlobalNetwork,
                    icon = Icons.Filled.Language,
                    title = "Global Network",
                    screen = Screens.Dummy,
                    dstArgs = "Global network stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyGlobalNetwork,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyNotifications,
                    icon = Icons.Filled.MarkAsUnread,
                    title = "Notifications",
                    screen = Screens.Dummy,
                    dstArgs = "Notifications stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyNotifications,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyPricing,
                    icon = Icons.Filled.Paid,
                    title = "Pricing",
                    screen = Screens.Dummy,
                    dstArgs = "Pricing stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyPricing,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyPaymentMethod,
                    icon = Icons.Filled.Payment,
                    title = "Payment",
                    screen = Screens.Dummy,
                    dstArgs = "Payment method stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyPaymentMethod,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyAnimalCategories,
                    icon = Icons.Filled.Pets,
                    title = "Categories",
                    screen = Screens.Dummy,
                    dstArgs = "Animal categories go here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyAnimalCategories,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyChat,
                    icon = Icons.Filled.QuestionAnswer,
                    title = "Chat",
                    screen = Screens.Dummy,
                    dstArgs = "Chat stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyChat,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyRescuers,
                    icon = Icons.Filled.Support,
                    title = "Rescuers",
                    screen = Screens.Dummy,
                    dstArgs = "Rescuers stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyRescuers,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyCalendar,
                    icon = Icons.Filled.Today,
                    title = "Calendar",
                    screen = Screens.Dummy,
                    dstArgs = "Calendar stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyCalendar,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyVerification,
                    icon = Icons.Filled.VerifiedUser,
                    title = "Verification",
                    screen = Screens.Dummy,
                    dstArgs = "Verification stuff goes here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyVerification,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyVideos,
                    icon = Icons.Filled.VideoLibrary,
                    title = "Videos",
                    screen = Screens.Dummy,
                    dstArgs = "Videos go here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyVideos,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyAudio,
                    icon = Icons.Filled.VolumeDown,
                    title = "Audio Tracks",
                    screen = Screens.Dummy,
                    dstArgs = "Audio trackes go here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyAudio,
                    onNavItemClick = onNavItemClick
                )
                NavMenuItem(
                    menuId = NavMenuConstants.MenuDummyLocations,
                    icon = Icons.Filled.LocationOn,
                    title = "Locations",
                    screen = Screens.Dummy,
                    dstArgs = "Location stuff go here...",
                    selected = currentMenuId == NavMenuConstants.MenuDummyLocations,
                    onNavItemClick = onNavItemClick
                )
            }

            Image(
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(id = R.drawable.nav_drawer_bg),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}