package dev.wirespec.adoptme.ui.screens.petslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import dev.wirespec.adoptme.da.web.PetsThumbnailImagesPath
import dev.wirespec.adoptme.models.PetListItemInfo

@Composable
fun PetGridRow(
    petListItem: PetListItemInfo,
    petItems: List<PetListItemInfo>,
    colWidth: Int,
    onItemClick: (PetListItemInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    // If the item that is being requested appears in the first column, return it and all the
    // other items following it that make up the row.

    val pos = (petListItem.position + 1) % 3

    if (pos != 1)
        return

    var lastItemPos = petListItem.position + 2

    if (lastItemPos > petItems.size - 1)
        lastItemPos = petItems.size - 1

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(colWidth.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (c in petListItem.position until lastItemPos + 1) {
            val pet = petItems[c]

            Box(
                modifier = modifier
                    .requiredWidth((colWidth - 1).dp)
                    .requiredHeight((colWidth - 1).dp)
            ) {
                Image(
                    painter = rememberCoilPainter(
                        "$PetsThumbnailImagesPath${pet.id}-1.jpg",
                        fadeIn = false
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize()
                        .clickable {
                            onItemClick(pet)
                        },
                    contentScale = ContentScale.Fit
                )

                Row(modifier = modifier.padding(top = 110.dp)) {
                    Text(
                        pet.name, modifier = modifier
                            .width(colWidth.dp)
                            .padding(start = 5.dp), color = Color.White
                    )
                }
            }
        }
    }
}
