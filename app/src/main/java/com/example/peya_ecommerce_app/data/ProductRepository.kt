package com.example.peya_ecommerce_app.data

import com.example.peya_ecommerce_app.model.Product

object ProductRepository {
    fun getProducts(): List<Product> {
        return listOf( //agregar ids
            Product("Ramen", 12000.0, "https://picsum.photos/80?random=1", "Comida"),
            Product("Sushi", 15000.0, "https://picsum.photos/80?random=2", "Comida"),
            Product("Soju", 2900.0, "https://picsum.photos/80?random=3", "Bebida"),
            Product("Agua saborizada", 1600.0, "https://picsum.photos/80?random=4", "Bebida"),
            Product("Helado", 1800.0, "https://picsum.photos/80?random=5", "Postre"),
            Product("Cheese Cake", 4000.0, "https://picsum.photos/80?random=6", "Postre"),
            Product("Fernet", 12000.0, "https://picsum.photos/80?random=7", "Bebida"),
            Product("Risotto", 7000.0, "https://picsum.photos/80?random=8", "Comida"),
            Product("Alfajor", 7000.0, "https://picsum.photos/80?random=9", "Postre"),
            Product("Porción Asado", 12000.0, "https://picsum.photos/80?random=10", "Comida"),
            Product("Té de Tilo", 12000.0, "https://picsum.photos/80?random=11", "Bebida")
            )
    }
}
