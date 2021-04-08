package dev.wirespec.adoptme.ui.screens.petslist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dev.wirespec.adoptme.models.PetListItemInfo
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.components.ListLoadingIndicator
import dev.wirespec.adoptme.ui.screens.ScreenGlobals
import dev.wirespec.adoptme.ui.screens.main.MainViewModel
import dev.wirespec.adoptme.ui.theme.AppColors
import dev.wirespec.adoptme.utils.DeviceUtils
import dev.wirespec.navigation.NavigationInfo
import dev.wirespec.navigation.NavigationManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/*
 * This composable is used to display the list of pets for adoption.
 * NOTE: The code below is only temporary. At the time of development, Compose was
 * available in Alpha08 and LazyColumn didn't support maintaining the scroll state even
 * though it provided a state parameter that took a LazyListState. But setting this
 * didn't do anything and the list's scroll position was lost whenever this component
 * was recomposed. Changing the device's orientation can be used to generate a recomposition.
 *
 * As a temporary solution until Google implements the scroll state, a Column is used instead.
 * For this to work, a snapshot needs to be taken after the data has been retrieved. There are
 * actually 60 cats but since they are loaded by batches of 20 with a pre-fetch of 20 more, that
 * results in 40 being retrieved on a Pixel 2 XL device. But since the LazyColumn's content
 * rendering has been commented out, any new items past the initial 40 will not be retrieved since
 * it isn't possible to scroll the LazyColumn when no items are displayed. For this reason, the
 * code below just limits the Column list to 60 items.
 *
 * Scroll state for Columns does work and for the demonstration of this app to show how state is
 * maintained when returning to a previous screen, the Column composable is being temporarily used.
 *
 * Using the Paging library 3.0 is the preferred way of retrieving items from the backend, so that
 * code is what is still being used.
 */

@ExperimentalMaterialApi
@Composable
fun PetsListHandler(navInfo: NavigationInfo, modifier: Modifier = Modifier) {
    val vm = navInfo.viewmodel as PetsListViewModel
    val vmMain: MainViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    PetsList(
        pets = vm.pets,
        scrollState = vm.scrollState,
        modifier = modifier,
        onItemClick = { petInfo ->
            NavigationManager.navigateTo(screen = Screens.PetDetails, screenData = petInfo)
        },
        onToolbarMenuClick = {
            coroutineScope.launch {
                vmMain.scaffoldState.drawerState.open()
            }
        })
}

@Composable
fun PetsList(
    pets: Flow<PagingData<PetListItemInfo>>,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    onItemClick: (petInfo: PetListItemInfo) -> Unit,
    onToolbarMenuClick: () -> Unit
) {
    val petItems = pets.collectAsLazyPagingItems()
    var initialized by remember { mutableStateOf(false) }

    if ((petItems.loadState.refresh != LoadState.Loading) && initialized) {

        val toolbarHeight = ScreenGlobals.DefaultToolbarHeight
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }

        // Offset to collapse toolbar
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

        // Create a connection to the nested scroll system and listen to the scroll happening inside child Column
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                    return Offset.Zero
                }
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            Surface(modifier = modifier.fillMaxSize()) {
                Row(modifier = modifier.fillMaxSize()) {
                    val colWidth = (DeviceUtils.screenRectDp.width() / 3).toInt()

                    Box(
                        modifier = modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)

                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .requiredHeight(toolbarHeight)
                            ) {
                            }

                            petItems.snapshot().items.forEach { pet ->
                                PetGridRow(
                                    petListItem = pet,
                                    petItems = petItems.snapshot().items,
                                    colWidth = colWidth,
                                    onItemClick = { petSelected ->
                                        onItemClick(petSelected)
                                    })
                            }
                        }
                    }
                }
            }

            TopAppBar(
                modifier = Modifier
                    .height(toolbarHeight)
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
                elevation = 0.dp,
                title = {},
                navigationIcon = {
                    IconButton(onClick = onToolbarMenuClick) {
                        Icon(
                            tint = AppColors.turquoise,
                            imageVector = Icons.Filled.Menu,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    } else {
        ListLoadingIndicator()
        initialized = true
    }
}

