package data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class RequiredArticle(
    @JsonNames("art_id")
    val id : Int,
    @JsonNames("amount_of")
    val quantity : Int
)
