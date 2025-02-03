package org.example.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv

/**
 * Clase encargada de gestionar la conexión con la base de datos MongoDB.
 * Utiliza un patrón Singleton para asegurar que solo haya una instancia activa.
 */
class DatabaseManager {
    private val dbName = "cserben"
    private val dotenv = dotenv()
    private val connectionString = dotenv["URL_MONGODB"]
    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null

    companion object {
        private var instance: DatabaseManager? = null

        /**
         * Obtiene la instancia única de `DatabaseManager`. Si no existe, la crea.
         * @return Instancia única de `DatabaseManager`.
         */
        fun getInstance(): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager()
            }
            return instance!!
        }
    }

    /**
     * Establece la conexión con la base de datos si no está conectada previamente.
     * @return La base de datos conectada.
     * @throws Exception Si hay un error al conectar con MongoDB.
     */
    fun connect(): MongoDatabase {
        if (mongoClient == null || database == null) {
            try {
                mongoClient = MongoClients.create(connectionString)
                database = mongoClient?.getDatabase(dbName)
                println("Conexión a la base de datos establecida")
            } catch (e: Exception) {
                println("Error al conectar con la base de datos: ${e.message}")
                throw e
            }
        }
        return database!!
    }

    /**
     * Cierra la conexión con la base de datos.
     * Si la conexión ya está cerrada, no hace nada.
     */
    fun disconnect() {
        try {
            mongoClient?.close()
        }catch (e:Exception){
            println(e.message)
        }
    }
}