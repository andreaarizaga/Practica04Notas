package com.example.practica04.data

import android.content.Context
import com.example.practica04.room.ProductDatabase

class ProductRepository(context: Context) {

    // Obtén la instancia de la base de datos usando el método `getInstance` de ProductDatabase
    private val db = ProductDatabase.getInstance(context)

    // Accede al DAO desde la instancia de la base de datos
    val productsDao = db.productsDao()

    // Aquí se puede agregar más lógica si es necesario
}
