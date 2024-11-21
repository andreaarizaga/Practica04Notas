package com.example.practica04.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practica04.model.Producto

@Database(
    entities = [Producto::class],
    version = 3,  // Asegúrate de que la versión esté correctamente configurada
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        // Definir la migración de la versión 1 a la 3
        private val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Asegúrate de que la migración esté correctamente definida
                // Por ejemplo, si se añadieron nuevas columnas o tablas, se definen aquí.
                database.execSQL(
                    "ALTER TABLE products ADD COLUMN new_column INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        private val migration2To3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aquí defines las migraciones para la versión 2 a la 3
                database.execSQL(
                    "ALTER TABLE products ADD COLUMN another_column TEXT"
                )
            }
        }

        // Método para obtener una instancia de la base de datos
        fun getInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    .addMigrations(migration1To2, migration2To3) // Asegúrate de agregar todas las migraciones
                    .fallbackToDestructiveMigration() // Destruir la base de datos si no se puede migrar
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
