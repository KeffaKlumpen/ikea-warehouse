import java.io.File

fun main() {
    println("Running IKEA Warehouse...")
    val resourceFolder = File("src/main/resources")
    val inventoryFile = File(resourceFolder, "inventory.json")
    val productsFile = File(resourceFolder, "products.json")

    WarehouseApp(inventoryFile, productsFile)
        .launch()
}