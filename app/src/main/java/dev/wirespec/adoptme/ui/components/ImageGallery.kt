package dev.wirespec.adoptme.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import dev.wirespec.adoptme.da.web.PetsLargeImagesPath
import dev.wirespec.adoptme.da.web.PetsThumbnailImagesPath
import dev.wirespec.adoptme.models.PetListItemInfo

@Composable
fun ImageGallery(pet: PetListItemInfo, modifier: Modifier = Modifier) {
    var selectedThumbnailNumber by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberCoilPainter(
                PetsLargeImagesPath + pet.id + "-" + selectedThumbnailNumber + ".jpg",
                fadeIn = true
            ),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(400.dp),
            contentScale = ContentScale.Crop
        )

        if (pet.imageCount > 1) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .requiredHeight(100.dp)
                    .padding(top = 4.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                for (i in 1 until pet.imageCount + 1) {
                    Column(
                        modifier = modifier
                            .requiredWidth(94.dp)
                            .requiredHeight(100.dp)
                    ) {

                        Image(
                            painter = rememberCoilPainter(
                                PetsThumbnailImagesPath + pet.id + "-" + i + ".jpg",
                                fadeIn = true
                            ),
                            contentDescription = null,
                            modifier = modifier
                                .width(90.dp)
                                .height(90.dp)
                                .clickable {
                                    selectedThumbnailNumber = i
                                },
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}