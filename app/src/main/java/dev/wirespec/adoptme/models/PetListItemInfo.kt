package dev.wirespec.adoptme.models

data class PetListItemInfo (
    var id: Int = 0,
    var name: String = "",
    var birthdate: String = "",
    var gender: String = "",
    var color: String = "",
    var type: String = "",
    var description: String = "",
    var imageCount: Int = 1,
    var position: Int = 0
)