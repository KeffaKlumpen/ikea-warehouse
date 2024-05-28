package data.repository

import data.model.Product

interface ProductRepository {
    fun getAllProducts() : List<Product>
    fun getAvailableProducts() : List<Product>
    fun sellProduct(product: Product): Boolean
    fun isProductAvailable(product: Product): Boolean
    fun getProduct(productName: String): Product?
}