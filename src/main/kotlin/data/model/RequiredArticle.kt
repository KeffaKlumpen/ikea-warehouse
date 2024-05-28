package data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
data class RequiredArticle(
    @JsonNames("art_id")
    val id : Int,
    val quantity : Int
)
