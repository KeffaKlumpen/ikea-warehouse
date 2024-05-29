package view

import data.model.Article
import data.model.Product

class ConsoleView {

    fun showMainMenu(onChoice : (Int) -> Unit, onExit : () -> Unit) {
        printMainMenu()
        var response = readln()
        while (response.lowercase() != "q") {
            try {
                val choice = response.toInt()
                onChoice(choice)
                return
            } catch (e : NumberFormatException) {
                println("Invalid response!")
            }
            printMainMenu()
            response = readln()
        }
        onExit()
    }

    fun showInventory(articles : List<Article>, onExit : () -> Unit) {
        printTitle("Inventory")
        printCollectionBuffered(articles) { _, item ->
            println("${item.id} | ${item.name} | ${item.stock}")
        }
        println("Press ENTER to return...")
        readln()
        onExit()
    }

    fun showProducts(products: List<Product>, onExit: () -> Unit) {
        printTitle("Products")
        if (products.isEmpty()) {
            message("No available products!")
            onExit()
            return
        }

        println("index | name | price | availability")
        printCollectionBuffered(products) { _, product ->
            println("${product.name} | ${product.price}")
            println("Articles: ${product.requiredArticles}")
        }
        println("Press ENTER to return...")
        readln()
        onExit()
    }

    fun showAvailableProducts(
        products: List<Product>,
        onResponse: (String) -> Unit,
        onExit: () -> Unit)
    {
        printTitle("AvailableProducts")
        if (products.isEmpty()) {
            message("No available products!")
            onExit()
            return
        }
        println("index | name | price")
        printCollectionBuffered(products) { _, product ->
            println("${product.name} | ${product.price}")
            println("    id | quantity")
            product.requiredArticles.forEach { requiredArticle ->
                println("    ${requiredArticle.id} | ${requiredArticle.quantity}")
            }
        }
        println("Enter a product name to buy 1 copy")
        println("Enter anything else to return to main menu.")
        val response = readln()
        if (response.isNotBlank()) {
            onResponse(response)
        } else {
            onExit()
        }
    }


    fun message(msg : String, waitForInput: Boolean = true) {
        println(msg)
        if(waitForInput) readln()
    }


    private fun printMainMenu() {
        printTitle("Welcome to IKEA!")
        println("1. Show inventory")
        println("2. Show products")
        println("3. Show available products")
        println("[Q] to quit the application.")
    }

    private fun <T> printCollectionBuffered (collection : List<T>, forEach : (index : Int, item : T) -> Unit) {
        collection.forEachIndexed { index, item ->
            forEach(index, item)
            if(index > 0 && index % 10 == 0) {
                println("Press ENTER to show more...")
                readln()
            }
        }
    }

    private fun printTitle(title : String) {
        println()
        println("----- $title -----")
    }
}

