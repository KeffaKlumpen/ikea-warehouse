package data.repository

import data.model.Product
import data.model.RequiredArticle

class ProductRepositoryRAM(private val inventoryRepo : InventoryRepository) : ProductRepository {
    private val products = mutableListOf(
        Product(
            "Chair",
            44,
            listOf(
                RequiredArticle(1, 4),
                RequiredArticle(3, 3)
            )
        ),
        Product(
            "Table",
            99,
            listOf(
                RequiredArticle(1, 10),
                RequiredArticle(5, 5)
            )
        )
    )

    override fun getAllProducts(): List<Product> {
        return products
    }

    override fun getAvailableProducts(): List<Product> {
        return products.filter { product ->
            product.requiredArticles.all { requiredArticle ->
                inventoryRepo.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
            }
        }
    }

    override fun getProduct(productName: String) : Product? {
        return products.find { it.name == productName }
    }

    override fun isProductAvailable(product: Product) : Boolean {
        return product.requiredArticles.all { requiredArticle ->
            inventoryRepo.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
        }
    }

    override fun sellProduct(product: Product) : Boolean {
        if (this.isProductAvailable(product)) {
            product.requiredArticles.forEach { requiredArticle ->
                inventoryRepo.reduceStock(requiredArticle.id, requiredArticle.quantity)
                inventoryRepo.getStockOfArticle(requiredArticle.id) >= requiredArticle.quantity
            }
            return true
        } else {
            return false
        }
    }
}