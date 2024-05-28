import data.repository.InventoryRepository
import data.repository.InventoryRepositoryJSON
import data.repository.ProductRepository
import data.repository.ProductRepositoryRAM
import data.view.ConsoleView
import java.io.File

class WarehouseApp(private val inventoryFile: File, private val productsFile: File) {
    fun launch() {
        val inventoryRepo : InventoryRepository = InventoryRepositoryJSON(inventoryFile)
        val productRepo : ProductRepository = ProductRepositoryRAM(inventoryRepo)

        ConsoleView(productRepo, inventoryRepo)
            .showMainMenu()
    }
}