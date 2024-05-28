package data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductList(
    val products : List<Product>
)