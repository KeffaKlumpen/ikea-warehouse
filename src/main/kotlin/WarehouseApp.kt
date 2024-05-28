import data.repository.*
import data.view.ConsoleView
import java.io.File

class WarehouseApp(private val inventoryFile: File, private val productsFile: File) {
    fun launch() {
        val inventoryRepository : InventoryRepository = InventoryRepositoryJSON(inventoryFile)
        val productRepository : ProductRepository = ProductRepositoryJSON(inventoryRepository, productsFile)

        ConsoleView(productRepository, inventoryRepository)
            .showMainMenu()
    }
}