package data.repository

import data.model.Article

class InventoryRepositoryRAM : InventoryRepository {
    private val inventory = mutableListOf(
        Article(1, "ok", 11),
        Article(2, "ok2", 22),
        Article(3, "ok3", 33),
    )

    override fun getAllArticles(): List<Article> {
        return inventory.toList()
    }

    override fun addStock(articleId: Int, quantity: Int): Boolean {
        val listIndex = inventory.indexOfFirst { it.id == articleId }
        val currentStock = inventory[listIndex].stock
        inventory[listIndex] = inventory[listIndex].copy(
            stock = currentStock + quantity
        )
        return true
    }

    override fun getStockOfArticle(articleId: Int): Int {
        inventory.find { it.id == articleId }.let { return it?.stock ?: 0 }
    }

    override fun reduceStock(articleId: Int, quantity : Int) : Boolean {
        val listIndex = inventory.indexOfFirst { it.id == articleId }
        val currentStock = inventory[listIndex].stock
        if (currentStock >= quantity) {
            inventory[listIndex] = inventory[listIndex].copy(
                stock = currentStock - quantity
            )
            return true
        } else {
            println("Failed to reduce stock in inventory - Can't reduce stock below 0!")
            return false
        }
    }
}