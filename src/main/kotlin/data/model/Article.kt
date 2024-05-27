package data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Article(
    @JsonNames("art_id")
    val id : Int,
    val name : String,
    val stock : Int
)
