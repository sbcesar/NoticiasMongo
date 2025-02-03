package org.example.services

import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.entity.Usuario
import org.example.repositories.UsuarioRepository

class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun register(email: String, nombre: String, nick: String, estado: Estado, direccion: Direccion, telefono: List<String>): Usuario? {

        val usuarioExistente = usuarioRepository.findByEmailAndNick(email, nick)

        if (usuarioExistente != null) {
            return null
        }

        val usuarioRegistrar = Usuario(email, nombre, nick, estado, direccion, telefono)

        usuarioRepository.register(usuarioRegistrar)
        return usuarioRegistrar
    }

    fun login(email: String, nick: String): Usuario? {
        val nuevoUsuario = usuarioRepository.findByEmailAndNick(email, nick)

        return nuevoUsuario
    }
}