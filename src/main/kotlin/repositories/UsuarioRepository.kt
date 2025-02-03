package org.example.repositories

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.entity.Usuario


class UsuarioRepository(private val database: MongoDatabase) {

    private var collName = "usuarios"

    fun findByEmailAndNick(email: String, nick: String): Usuario? {
        val usuarioCollection = database.getCollection(collName, Usuario::class.java)

        val busqueda = Filters.and(Filters.eq("email", email), Filters.eq("apodo", nick))
        return usuarioCollection.find(busqueda).firstOrNull()
    }

    fun register(usuario: Usuario) {
        val usuarioCollection = database.getCollection(collName, Usuario::class.java)
        usuarioCollection.insertOne(usuario)
    }

}