package org.example.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document

class DatabaseManager {
    private val dbName = "cserben"
    private val dotenv = dotenv()
    private val connectionString = dotenv["URL_MONGODB"]
    private lateinit var mongoClient: MongoClient
    private lateinit var database: MongoDatabase


    fun connect() {
        try {
            mongoClient = MongoClients.create(connectionString)
            database = mongoClient.getDatabase(dbName)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun getCollection(collectionName: String): MongoCollection<Document> {
        return database.getCollection(collectionName)
    }

    fun disconnect() {
        try {
            mongoClient.close()
        }catch (e:Exception){
            println(e.message)
        }
    }
}