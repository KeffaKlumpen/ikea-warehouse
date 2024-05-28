package data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
data class Product(
    val name : String,
    val price : Int,
    @JsonNames("contain_articles")
    val requiredArticles : List<RequiredArticle>
)
