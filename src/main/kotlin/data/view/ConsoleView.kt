package data.view

import data.repository.InventoryRepository
import data.repository.ProductRepository

class ConsoleView(
    private val productRepository: ProductRepository,
    private val inventoryRepository: InventoryRepository) {

    private fun <T> showCollectionBuffered (collection : List<T>, forEach : (index : Int, item : T) -> Unit) {
        collection.forEachIndexed { index, item ->
            forEach(index, item)
            if(index > 0 && index % 10 == 0) {
                println("Press ENTER to show more...")
                readln()
            }
        }
    }

    fun showMainMenu() {
        printTitle("Welcome to IKEA!")
        println("1. Show inventory")
        println("2. Show products")
        println("3. Show available products")
        println("[Q] to quit the application.")
        val response = readln()
        if (response.lowercase() == "q") {
            return
        }
        try {
            val choice = response.toInt()
            if (choice !in 1..3) {
                throw NumberFormatException("Value is outside range!")
            }
            when(choice) {
                1 -> showInventory()
                2 -> showProducts()
                3 -> showAvailableProducts()
            }
        } catch (e : NumberFormatException) {
            println("Invalid response!")
            showMainMenu()
        }
    }

    private fun showAvailableProducts() {
        printTitle("AvailableProducts")
        val products = productRepository.getAvailableProducts()
        if (products.isEmpty()) {
            println("No available products!")
        } else {
            println("index | name | price")
            showCollectionBuffered(products) { index, product ->
                println("$index | ${product.name} | ${product.price}")
                println("    id | quantity")
                product.requiredArticles.forEach { requiredArticle ->
                    println("    ${requiredArticle.id} | ${requiredArticle.quantity}")
                }
            }
            println("Enter a product index to buy 1 copy")
            println("Enter anything else to return to main menu.")
            val response = readln()
            if (response.isNotBlank()) {
                val productToSell = productRepository.getProduct(response)
                if (productToSell != null) {
                    val sellAccepted = productRepository.sellProduct(productToSell)
                    if(sellAccepted) {
                        println("Sold 1 product: $response")
                    } else {
                        println("Purchase failed. Are you sure the product $response is in stock?")
                    }
                } else {
                    println("No product found with name: $response")
                }
            }
        }
        println("Press ENTER to return...")
        readln()
        showMainMenu()
    }

    private fun showProducts() {
        printTitle("Products")
        productRepository.getAllProducts().also { products ->
            println("index | name | price | availability")
            showCollectionBuffered(products) { index, product ->
                val available = productRepository.isProductAvailable(product)
                println("$index | ${product.name} | ${product.price} | ${if (available) "In stock" else "Unavailable"}")
                println("    id | quantity")
                product.requiredArticles.forEach { requiredArticle ->
                    println("    ${requiredArticle.id} | ${requiredArticle.quantity}")
                }
            }
        }
        println("Press ENTER to return...")
        readln()
        showMainMenu()
    }

    private fun showInventory() {
        printTitle("Inventory")
        showCollectionBuffered(inventoryRepository.getAllArticles()) { _, item ->
            println("${item.id} | ${item.name} | ${item.stock}")
        }
        println("Press ENTER to return...")
        readln()
        showMainMenu()
    }
}

fun printTitle(title : String) {
    println()
    println("----- $title -----")
}