import controller.ConsoleViewController
import data.repository.*
import java.io.File

class WarehouseApp(private val inventoryFile: File, private val productsFile: File) {
    fun launch() {
        val inventoryRepository : InventoryRepository = InventoryRepositoryJSON(inventoryFile)
        val productRepository : ProductRepository = ProductRepositoryJSON(inventoryRepository, productsFile)

        ConsoleViewController(productRepository, inventoryRepository).launch()
    }
}