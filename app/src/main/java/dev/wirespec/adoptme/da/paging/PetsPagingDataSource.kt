package dev.wirespec.adoptme.da.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.wirespec.adoptme.da.Repository
import dev.wirespec.adoptme.da.web.PetAPIConfig
import dev.wirespec.adoptme.da.web.PetAPIOptions
import dev.wirespec.adoptme.models.PetListItemInfo

class PetsPagingDataSource(private val petAPIOptions: PetAPIOptions) : PagingSource<Int, PetListItemInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PetListItemInfo> {
        val startPos = params.key ?: 0

        petAPIOptions.startPos = startPos
        petAPIOptions.pageSize = PetAPIConfig.PagingSize

        return try {
            val pets = Repository.getPets(petAPIOptions)

            // Store the position of each item out of the total items. This is needed to help
            // the grid layout to determine which column the item will appear under.
            pets.forEachIndexed { index, pet ->
                pet.position = petAPIOptions.startPos  + index
            }

            val prevKey = if (startPos > 0) startPos - PetAPIConfig.PagingSize else null
            val nextKey = if (pets.isNotEmpty()) startPos + PetAPIConfig.PagingSize else null

            LoadResult.Page(
                data = pets,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PetListItemInfo>): Int {
        return 0
    }
}