package org.example.utils

class Menu {

    fun showMenu() {
        var bandera = true

        while (bandera) {
            println("\n--- Menú de Gestión de Noticias ---")
            println("1. Publicar noticia")
            println("2. Mostrar las noticias de un usuario")
            println("3. Mostrar los comentarios de una noticia")
            println("4. Buscar noticia por etiqueta")
            println("5. Mostrar las 10 ultimas noticias")
            println("6. Salir")
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
}