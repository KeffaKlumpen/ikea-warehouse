package data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Product(
    val name: String,
    val price: Int = Int.MAX_VALUE,           // TODO: Warn/throw about missing price parameter
    @JsonNames("contain_articles")
    val requiredArticles: List<RequiredArticle>
)

