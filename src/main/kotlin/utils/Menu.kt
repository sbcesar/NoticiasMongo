package org.example.utils

import org.example.controller.UsuarioController
import org.example.entity.Direccion
import org.example.entity.Estado

class Menu(private val usuarioController: UsuarioController) {

    fun showAuthMenu() {
        var authSuccess = false

        while (!authSuccess) {
            println("\n--- Bienvenido al Sistema de Noticias ---")
            println("1. Registrarse")
            println("2. Iniciar sesión")
            println("3. Salir")
            print("Seleccione una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> authSuccess = register()
                2 -> authSuccess = login()
                3 -> {
                    println("Saliendo...")
                    return
                }
                else -> println("Opción no válida, intentelo de nuevo")
            }
        }

        showMainMenu()
    }

    fun showMainMenu() {
        var bandera = true

        while (bandera) {
            println("\n--- Menú de Gestión de Noticias ---")
            println("1. Publicar noticia")
            println("2. Mostrar las noticias de un usuario")
            println("3. Mostrar los comentarios de una noticia")
            println("4. Buscar noticia por etiqueta")
            println("5. Mostrar las 10 ultimas noticias")
            println("6. Cerrar Sesión")
            print("Seleccione una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> {}
                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
                6 -> {
                    println("Saliendo...")
                    bandera = false
                }
                else -> println("Opción no válida, intentelo de nuevo")
            }
        }
    }

    private fun register(): Boolean {
        println("\n--- Registro de Usuario ---")

        print("Ingrese su email: ")
        val email = readlnOrNull().orEmpty()

        print("Ingrese su nombre: ")
        val nombre = readlnOrNull().orEmpty()

        print("Ingrese su nick: ")
        val nick = readlnOrNull().orEmpty()

        //------------------ Dirección ------------------
        println("\n--- Ingrese su Dirección ---")

        print("Ingrese la calle: ")
        val calle = readlnOrNull().orEmpty().trim().ifEmpty { "Desconocida" }

        print("Ingrese el número: ")
        val num = readlnOrNull().orEmpty().trim().ifEmpty { "0" }

        print("Ingrese el código postal: ")
        val cp = readlnOrNull().orEmpty().trim().ifEmpty { "00000" }

        print("Ingrese la ciudad: ")
        val ciudad = readlnOrNull().orEmpty().trim().ifEmpty { "Desconocida" }

        val direccion = Direccion(calle, num, cp, ciudad)
        // ------------------ ......... ------------------

        print("Ingrese su teléfono (puede ingresar varios separados por comas): ")
        val telefonos = readlnOrNull().orEmpty()
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        val usuarioRegistrado = usuarioController.register(email, nombre, nick, Estado.INACTIVO, direccion, telefonos)
        println(usuarioRegistrado)
        return true
    }

    private fun login(): Boolean {
        println("\n--- Inicio de Sesión ---")

        print("Ingrese su email: ")
        val email = readlnOrNull().orEmpty()

        print("Ingrese su apodo: ")
        val apodo = readlnOrNull().orEmpty()

        val resultado = usuarioController.login(email, apodo)
        println(resultado)
        return true
    }
}