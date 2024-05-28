package data.repository

import data.model.Article

interface InventoryRepository {
    fun getAllArticles(): List<Article>
    fun getStockOfArticle(articleId: Int) : Int
    fun addStock(articleId : Int, quantity : Int)
    fun reduceStock(articleId: Int, quantity: Int)
}