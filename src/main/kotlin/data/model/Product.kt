package data.model

data class Product(
    val name : String,
    val price : Int,
    val requiredArticles : Pair<Int, Int>
)
