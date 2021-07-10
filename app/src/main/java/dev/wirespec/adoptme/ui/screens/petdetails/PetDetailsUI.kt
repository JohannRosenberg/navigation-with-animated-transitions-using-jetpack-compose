package dev.wirespec.adoptme.ui.screens.petdetails

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.wirespec.adoptme.R
import dev.wirespec.adoptme.models.PetListItemInfo
import dev.wirespec.adoptme.ui.Screens
import dev.wirespec.adoptme.ui.components.DetailProperty
import dev.wirespec.adoptme.ui.components.ImageGallery
import dev.wirespec.adoptme.ui.components.MultiLineText
import dev.wirespec.adoptme.ui.screens.ScreenGlobals
import dev.wirespec.adoptme.ui.theme.AppColors
import dev.wirespec.adoptme.ui.theme.AppTheme
import dev.wirespec.adoptme.ui.theme.MaterialColors
import dev.wirespec.navigation.NavigationInfo
import dev.wirespec.navigation.NavigationManager

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PetDetailsHandler(navInfo: NavigationInfo, modifier: Modifier = Modifier, screenIsClosing: Boolean = false) {

    val vm = navInfo.viewmodel as PetDetailsViewModel

    PetDetailsUI(
        modifier = modifier,
        pet = navInfo.screenData as PetListItemInfo,
        scrollState = vm.scrollState, // Once LazyColumn supports maintaining the scroll state, replace this with vm.listState
        onAdoptClick = {
            NavigationManager.navigateTo(screen = Screens.Dummy, screenData = "How to adopt")
        },
        onBackButtonClick = {
            NavigationManager.goBack()
        })
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PetDetailsUI(
    pet: PetListItemInfo,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    onAdoptClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.appColorTheme.materialColors.surface)
    ) {

        TopAppBar(
            modifier = Modifier.height(ScreenGlobals.DefaultToolbarHeight),
            elevation = 0.dp,
            title = {
                Text(pet.name, color = AppTheme.appColorTheme.materialColors.secondary)
            },
            navigationIcon = {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        modifier = modifier
                            .requiredWidth(ScreenGlobals.ToolbarBackButtonIconSize)
                            .requiredHeight(ScreenGlobals.ToolbarBackButtonIconSize),
                        tint = AppColors.turquoise,
                        imageVector = Icons.Filled.ArrowLeft,
                        contentDescription = ""
                    )
                }
            }
        )
        Column(
            verticalArrangement = Arrangement.Top, modifier = modifier
                .padding(10.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .border(width = 2.dp, color = MaterialColors.gray100, shape = RoundedCornerShape(10.dp))
                .background(AppColors.whiteAlpha)
                .verticalScroll(scrollState)
        ) {
            ImageGallery(pet)
            Text(
                pet.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 30.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                DetailProperty(R.string.gender, if (pet.gender == "m") stringResource(R.string.male) else stringResource(R.string.female))
                DetailProperty(R.string.born, pet.birthdate)
                DetailProperty(R.string.color, pet.color)
                //DetailProperty(R.string.type, pet.type)
            }
            MultiLineText(
                pet.description,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center, modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Button(
                    modifier = modifier,
                    colors = AppTheme.getButtonColors(),
                    elevation = ButtonDefaults.elevation(5.dp),
                    onClick = onAdoptClick
                ) {
                    Text(
                        text = stringResource(R.string.adopt) + " " + pet.name,
                        modifier = modifier.padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 7.dp)
                    )
                }
            }
        }
    }
}