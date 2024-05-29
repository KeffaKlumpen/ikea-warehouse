package controller

import data.repository.InventoryRepository
import data.repository.ProductRepository
import view.ConsoleView

class ConsoleViewController(
    private val productRepository: ProductRepository,
    private val inventoryRepository: InventoryRepository
) {
    private val consoleView : ConsoleView = ConsoleView()

    fun launch() {
        mainMenu()
    }

    private fun mainMenu() {
        consoleView.showMainMenu(
            onChoice = { choice ->
                if (choice !in 1..3) {
                    consoleView.message("Value is outside range!", true)
                    mainMenu()
                }
                when(choice) {
                    1 -> inventory()
                    2 -> allProducts()
                    3 -> availableProducts()
                }
            },
            onExit = {
                consoleView.message("Thank you for using IKEA Warehouse!", true)
            }
        )
    }

    private fun inventory() {
        consoleView.showInventory(
            articles = inventoryRepository.getAllArticles(),
            onExit = {
                mainMenu()
            }
        )
    }

    private fun allProducts() {
        consoleView.showProducts(
            products = productRepository.getAllProducts(),
            onExit = {
                mainMenu()
            }
        )
    }

    private fun availableProducts() {
        consoleView.showAvailableProducts(
            products = productRepository.getAvailableProducts(),
            onResponse = { response ->
                val productToSell = productRepository.getProduct(response)
                if (productToSell != null) {
                    val sellAccepted = productRepository.sellProduct(productToSell)
                    if(sellAccepted) {
                        consoleView.message("Sold 1 product: $response")
                    } else {
                        consoleView.message("Purchase failed. Make sure the product $response is available.")
                    }
                } else {
                    consoleView.message("No product found with name: $response")
                }
                mainMenu()
            },
            onExit = {
                mainMenu()
            }
        )
    }
}