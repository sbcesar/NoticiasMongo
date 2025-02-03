package org.example.controller

import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.entity.Usuario
import org.example.services.UsuarioService

class UsuarioController(private val usuarioService: UsuarioService) {

    fun register(email: String, nombre: String, apodo: String, estado: Estado, direccion: Direccion, telefono: List<String>): Usuario {
        if (email.isBlank() || nombre.isBlank() || apodo.isBlank()) {
            throw Exception("El email, nombre y apodo son obligatorios.")
        }

        if (telefono.isEmpty()) {
            throw Exception("Debe proporcionar al menos un número de teléfono.")
        }

        val usuario = usuarioService.register(email, nombre, apodo, estado, direccion, telefono)
            ?: throw Exception("El usuario ya está registrado")

        return usuario
    }

    fun login(email: String, nick: String): Usuario? {
        if (email.isBlank() || nick.isBlank()) {
            throw Exception("El email y el nick son obligatorios.")
        }

        return usuarioService.login(email, nick)
    }
}