package org.example.services

import org.bson.Document
import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.repositories.UsuarioRepository

class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun register(email: String, nombre: String, nick: String, estado: Estado, direccion: Direccion, telefono: List<String>): String {

        val usuarioExistente = usuarioRepository.findByEmailAndNick(email, nick)

        if (usuarioExistente != null) {
            return "El usuario $nick con email ($email) ya está registrado"
        }

        val usuarioDocumento = Document()
            .append("email", email)
            .append("nombre", nombre)
            .append("apodo", nick)
            .append("estado", estado.toString())
            .append("direccion", direccion)
            .append("telefono", telefono)

        return try {
            usuarioRepository.register(usuarioDocumento)
            "Usuario registrado"
        } catch (e: Exception) {
            "Error al registrar el usuario: ${e.message}"
        }
    }

    fun login(email: String, nick: String): String {
        val nuevoUsuario = usuarioRepository.findByEmailAndNick(email, nick)

        return if (nuevoUsuario != null) {
            "El usuario se logueó exitosamente"
        } else {
            "El usuario $nick con email ($email) no está registrado"
        }
    }
}