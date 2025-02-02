package org.example.repositories

import com.mongodb.client.model.Filters
import org.bson.Document
import org.example.config.DatabaseManager
import org.example.entity.Direccion
import org.example.entity.Estado


class UsuarioRepository(private val databaseManager:  DatabaseManager) {

    private var collName = "usuarios"

    fun register(email: String, nombre: String, apodo: String, estado: Estado, direccion: Direccion, telefono: List<String>): Document {
        val usuarioCollection = databaseManager.getCollection(collName)

        val usuarioDocumento = Document()
            .append("email", email)
            .append("nombre", nombre)
            .append("apodo", apodo)
            .append("estado", estado.toString())
            .append("direccion", direccion)
            .append("telefono", telefono)

        usuarioCollection.insertOne(usuarioDocumento)

        return usuarioDocumento
    }

    fun login(email: String, nick: String): Document {
        val usuarioCollection = databaseManager.getCollection(collName)

        val encontrarUsuario = Filters.and(Filters.eq("email", email), Filters.eq("nick", nick))

        val busqueda = usuarioCollection.find(encontrarUsuario)

        if (busqueda == null) {
            println("Login exitoso")
        } else {
            println("El usuario no está registrado")
        }
    }
}