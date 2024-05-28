package data.repository

import data.model.Article
import data.model.ArticleInventory
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class InventoryRepositoryJSON(private val inventoryFile : File) : InventoryRepository {
    override fun getAllArticles() : List<Article> {
        val json = inventoryFile.readText(Charsets.UTF_8)
        try {
            return Json.decodeFromString<ArticleInventory>(json).inventory
        } catch (e : SerializationException) {
            error("ERROR: inventory file not valid json!")
        } catch (e : IllegalStateException) {
            error("No file found!")
        }
    }

    override fun addStock(articleId: Int, quantity: Int) : Boolean {
        val articleInventory = getFromJson()

        val listIndex = articleInventory.inventory.indexOfFirst { it.id == articleId }
        val currentStock = articleInventory.inventory[listIndex].stock
        articleInventory.inventory[listIndex] = articleInventory.inventory[listIndex].copy(
            stock = currentStock + quantity)

        return updateJsonFile(articleInventory)
    }

    override fun getStockOfArticle(articleId: Int): Int {
        return getAllArticles().find { it.id == articleId }?.stock ?: 0
    }

    override fun reduceStock(articleId: Int, quantity: Int) : Boolean {
        val articleInventory = getFromJson()

        val listIndex = articleInventory.inventory.indexOfFirst { it.id == articleId }
        val currentStock = articleInventory.inventory[listIndex].stock
        if (currentStock >= quantity) {
            articleInventory.inventory[listIndex] = articleInventory.inventory[listIndex].copy(
                stock = currentStock - quantity)

            return updateJsonFile(articleInventory)
        } else {
            return false
        }
    }

    private fun getFromJson() : ArticleInventory {
        val json = inventoryFile.readText(Charsets.UTF_8)
        try {
            return Json.decodeFromString<ArticleInventory>(json)
        } catch (e : SerializationException) {
            error("ERROR: inventory file not valid json!")
        } catch (e : IllegalStateException) {
            error("No file found!")
        }
    }

    private fun updateJsonFile(inventory: ArticleInventory) : Boolean {
        inventoryFile.writeText(Json.encodeToString(inventory))
        return true
    }
}