package org.example.controller

import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.services.UsuarioService

class UsuarioController(private val usuarioService: UsuarioService) {

    fun register(email: String, nombre: String, apodo: String, estado: Estado, direccion: Direccion, telefono: List<String>): String {
        if (email.isBlank() || nombre.isBlank() || apodo.isBlank()) {
            return "El email, nombre y apodo son obligatorios."
        }

        if (!email.contains("@") || !email.contains(".")) {
            return "El email no tiene un formato válido."
        }

        if (telefono.isEmpty()) {
            return "Debe proporcionar al menos un número de teléfono."
        }

        return usuarioService.register(email, nombre, apodo, estado, direccion, telefono)
    }

    fun login(email: String, nick: String): String {
        if (email.isBlank() || nick.isBlank()) {
            return "El email y el nick son obligatorios."
        }

        return usuarioService.login(email, nick)
    }
}