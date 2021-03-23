package dev.wirespec.adoptme.da

import dev.wirespec.adoptme.da.web.AdoptMeWebAPI
import dev.wirespec.adoptme.da.web.PetAPIOptions
import dev.wirespec.adoptme.da.web.RetrofitClient
import dev.wirespec.adoptme.models.PetListItemInfo

class Repository {
    companion object {
        private var webApi: AdoptMeWebAPI = RetrofitClient.createRetrofitClient()

        suspend fun getPets(options: PetAPIOptions): List<PetListItemInfo> {
            return webApi.getPets(startPos = options.startPos, options.pageSize, if (options.sortDesc) "desc" else "asc")
        }
    }
}