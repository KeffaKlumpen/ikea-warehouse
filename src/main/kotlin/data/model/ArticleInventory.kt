package data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleInventory(
    val inventory: MutableList<Article>
)
