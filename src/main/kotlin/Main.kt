import data.repository.InventoryRepository
import data.repository.InventoryRepositoryRAM
import data.repository.ProductRepository
import data.repository.ProductRepositoryRAM
import data.view.ConsoleView

fun main() {
    println("Running IKEA Warehouse...")

    val inventoryRepo : InventoryRepository = InventoryRepositoryRAM()
    val productRepo : ProductRepository = ProductRepositoryRAM(inventoryRepo)

    ConsoleView(productRepo, inventoryRepo).showMainMenu()
}