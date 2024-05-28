package data.repository

import data.model.Product
import data.model.ProductList
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File

class ProductRepositoryJSON(
    private val inventoryRepository: InventoryRepository,
    private val productFile: File
) : ProductRepository {
    override fun getAllProducts(): List<Product> {
        val json = productFile.readText(Charsets.UTF_8)
        try {
            return Json.decodeFromString<ProductList>(json).products
        } catch (e : SerializationException) {
            e.printStackTrace()
            error("ERROR: product file not valid json!")
        } catch (e : IllegalStateException) {
            error("No file found!")
        }
    }

    override fun getAvailableProducts(): List<Product> {
        return getAllProducts().filter { product ->
            product.requiredArticles.all { requiredArticle ->
                inventoryRepository.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
            }
        }
    }

    override fun sellProduct(product: Product): Boolean {
        if (this.isProductAvailable(product)) {
            product.requiredArticles.forEach { requiredArticle ->
                inventoryRepository.reduceStock(requiredArticle.id, requiredArticle.quantity)
                inventoryRepository.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
            }
            return true
        } else {
            return false
        }
    }

    override fun isProductAvailable(product: Product): Boolean {
        return product.requiredArticles.all { requiredArticle ->
            inventoryRepository.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
        }
    }

    override fun getProduct(productName: String): Product? {
        return getAllProducts().find { it.name == productName }
    }
}