package org.example.repositories

import com.mongodb.client.model.Filters
import org.bson.Document
import org.example.config.DatabaseManager


class UsuarioRepository(private val databaseManager:  DatabaseManager) {

    private var collName = "usuarios"

    fun findByEmailAndNick(email: String, nick: String): Document? {
        val usuarioCollection = databaseManager.getCollection(collName)

        val busqueda = Filters.and(Filters.eq("email", email), Filters.eq("apodo", nick))

        return usuarioCollection.find(busqueda).firstOrNull()
    }

    fun register(usuario: Document) {
        val usuarioCollection = databaseManager.getCollection(collName)
        usuarioCollection.insertOne(usuario)
    }

}