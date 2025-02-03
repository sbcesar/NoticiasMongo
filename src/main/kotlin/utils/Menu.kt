package org.example.utils

import org.bson.types.ObjectId
import org.example.controller.ComentarioController
import org.example.controller.NoticiaController
import org.example.controller.UsuarioController
import org.example.entity.*
import java.util.*
import java.util.regex.Pattern

class Menu(
    private val usuarioController: UsuarioController,
    private val noticiaController: NoticiaController,
    private val comentarioController: ComentarioController
) {

    // Esta es una variable que guarda el usuario una vez se loguea para poder usarlo en el resto de consultas
    // Vuele a null cuando se desloguea
    private var usuarioActual: Usuario? = null

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

    private fun showMainMenu() {
        var bandera = true

        while (bandera) {
            println("\n--- Menú de Gestión de Noticias ---")
            println("1. Publicar noticia")
            println("2. Mostrar las noticias por usuario")
            println("3. Mostrar las noticias por etiquetas")
            println("4. Agregar cometario a la noticia")
            println("5. Mostrar los comentarios de la noticia")
            println("6. Mostrar las 10 ultimas noticias")
            println("7. Cerrar Sesión")
            print("Seleccione una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> {
                    if (usuarioActual == null) {
                        println("Debes iniciar sesión para publicar una noticia")
                    } else {
                        println("Ingrese el título de la noticia:")
                        val titulo = readlnOrNull().orEmpty()

                        println("Ingrese el cuerpo de la noticia:")
                        val cuerpo = readlnOrNull().orEmpty()

                        println("Ingrese las etiquetas de la noticia (separadas por coma):")
                        val etiquetas = readlnOrNull()?.split(",")?.map { it.trim() } ?: listOf()

                        val nuevaNoticia = Noticia(
                            _id = ObjectId(),
                            titulo = titulo,
                            cuerpo = cuerpo,
                            fech_publicacion = Date(),
                            autor = usuarioActual!!,
                            tag = etiquetas
                        )

                        noticiaController.publicarNoticia(nuevaNoticia)
                    }
                }
                2 -> {
                    println("Ingrese el apodo del usuario para mostrar sus noticias:")
                    val nick = readlnOrNull().orEmpty()
                    val noticias = noticiaController.listarNoticiasPorUsuario(nick)

                    if (noticias.isEmpty()) {
                        println("No se encontraron noticias con este nick ($nick)")
                    } else {
                        noticias.forEach { println(it) }
                    }
                }
                3 -> {
                    println("Ingresa las etiquetas de la noticia que quieres buscar (s -> salir):")
                    val tags = mutableListOf<String>()

                    while (true) {
                        val tag = readlnOrNull()?.trim()
                        if (tag.equals("s", ignoreCase = true)) break
                        if (!tag.isNullOrEmpty()) {
                            tags.add(tag)
                        } else {
                            println("Ingresa un tag válido.")
                        }
                    }
                    val noticias = noticiaController.buscarNoticiasPorEtiquetas(tags)

                    if (noticias.isEmpty()) {
                        println("No se encontraron noticias con estos tags (${tags.joinToString(", ")})")
                    } else {
                        noticias.forEach { println(it) }
                    }
                }
                4 -> {
                    if (usuarioActual == null) {
                        println("Debes iniciar sesión para publicar un comentario")
                    } else {
                        val noticias = noticiaController.listarTodas()

                        if (noticias.isEmpty()) {
                            println("No hay noticias disponibles para comentar.")
                        } else {
                            println("Noticias disponibles:")
                            noticias.forEach { println("- ${it.titulo}") }

                            println("Escribe el título de la noticia que quieres comentar: ")
                            val tituloNoticia = readlnOrNull().orEmpty()

                            val noticiasEncontradas = noticiaController.buscarNoticiaPorTitulo(tituloNoticia)

                            if (noticiasEncontradas.isEmpty()) {
                                println("No se encontró ninguna noticia con ese título.")
                            } else if (noticiasEncontradas.size == 1) {
                                val noticia = noticiasEncontradas.first()

                                println("Escribe tu comentario:")
                                val textoComentario = readlnOrNull().orEmpty()

                                val nuevoComentario = Comentario(
                                    usuario = usuarioActual!!,
                                    noticia = noticia,
                                    comentario = textoComentario,
                                    fecha = Date()
                                )

                                comentarioController.agregarComentario(nuevoComentario)
                                println("Comentario publicado con éxito.")
                            } else {
                                println("Se encontraron varias noticias con el mismo título. Seleccione una:")
                                noticiasEncontradas.forEachIndexed { index, noticia ->
                                    println("${index + 1}. ${noticia.titulo} (Publicada el: ${noticia.fech_publicacion})")
                                }

                                print("Ingrese el número de la noticia que desea comentar: ")
                                val seleccion = readlnOrNull()?.toIntOrNull()

                                if (seleccion == null || seleccion !in 1..noticiasEncontradas.size) {
                                    println("Selección inválida. No se publicará el comentario.")
                                } else {
                                    val noticiaSeleccionada = noticiasEncontradas[seleccion - 1]

                                    println("Escribe tu comentario:")
                                    val textoComentario = readlnOrNull().orEmpty()

                                    val nuevoComentario = Comentario(
                                        usuario = usuarioActual!!,
                                        noticia = noticiaSeleccionada,
                                        comentario = textoComentario,
                                        fecha = Date()
                                    )

                                    comentarioController.agregarComentario(nuevoComentario)
                                    println("Comentario publicado con éxito.")
                                }
                            }
                        }
                    }
                }
                5 -> {
                    if (usuarioActual == null) {
                        println("Debes iniciar sesión para ver los comentarios de una noticia")
                    } else {
                        noticiaController.listarTodas()

                        println("Elije la noticia (escribe su titulo porfi :p): ")
                        val titulo = readlnOrNull().orEmpty()

                        val noticiasEncontradas = noticiaController.buscarNoticiaPorTitulo(titulo)

                        if (noticiasEncontradas.isEmpty()) {
                            println("No se encontró ninguna noticia con ese título.")
                        } else if (noticiasEncontradas.size == 1) {
                            val noticia = noticiasEncontradas.first()

                            val comentarios = comentarioController.listarComentariosPorNoticia(noticia)

                            if (comentarios.isEmpty()) {
                                println("La noticia no tiene ningun comentario")
                            } else {
                                comentarios.forEach { println(it) }
                            }
                        } else {
                            println("Se encontraron varias noticias con el mismo título. Seleccione una:")
                            noticiasEncontradas.forEachIndexed { index, noticia ->
                                println("${index + 1}. ${noticia.titulo} (Publicada el: ${noticia.fech_publicacion})")
                            }

                            print("Ingrese el número de la noticia: ")
                            val seleccion = readlnOrNull()?.toIntOrNull()

                            if (seleccion == null || seleccion !in 1..noticiasEncontradas.size) {
                                println("Selección inválida. No se visualizarán los comentarios.")
                            } else {
                                val noticiaSeleccionada = noticiasEncontradas[seleccion - 1]

                                val comentarios = comentarioController.listarComentariosPorNoticia(noticiaSeleccionada)

                                if (comentarios.isEmpty()) {
                                    println("La noticia no tiene ningun comentario")
                                } else {
                                    comentarios.forEach { println(it) }
                                }
                            }
                        }
                    }
                }
                6 -> {
                    noticiaController.listarUltimasNoticias()
                }
                7 -> {
                    println("Saliendo...")
                    bandera = false
                }
                else -> println("Opción no válida, intentelo de nuevo")
            }
        }
    }

    private fun register(): Boolean {
        println("\n--- Registro de Usuario ---")

        while (true) {
            print("Ingrese su email: ")
            val email = readlnOrNull().orEmpty()

            if (!isValidEmail(email)) {
                println("Error: El email no tiene un formato válido. Inténtalo de nuevo.")
                continue
            }

            print("Ingrese su nombre: ")
            val nombre = readlnOrNull().orEmpty()

            if (nombre.isBlank()) {
                println("Error: El nombre no puede estar vacío. Inténtalo de nuevo.")
                continue
            }

            print("Ingrese su nick: ")
            val nick = readlnOrNull().orEmpty()

            if (nick.isBlank()) {
                println("Error: El nick no puede estar vacío. Inténtalo de nuevo.")
                continue
            }

            //------------------ Dirección ------------------
            println("\n--- Ingrese su Dirección ---")

            print("Ingrese la calle: ")
            val calle = readlnOrNull().orEmpty().trim()

            if (calle.isBlank()) {
                println("Error: La calle no puede estar vacía. Inténtalo de nuevo.")
                continue
            }

            print("Ingrese el número: ")
            val num = readlnOrNull().orEmpty().trim()

            if (num.isBlank()) {
                println("Error: El número no puede estar vacío. Inténtalo de nuevo.")
                continue
            }

            print("Ingrese el código postal: ")
            val cp = readlnOrNull().orEmpty().trim()

            if (cp.isBlank()) {
                println("Error: El código postal no puede estar vacío. Inténtalo de nuevo.")
                continue
            }

            print("Ingrese la ciudad: ")
            val ciudad = readlnOrNull().orEmpty().trim()

            if (ciudad.isBlank()) {
                println("Error: La ciudad no puede estar vacía. Inténtalo de nuevo.")
                continue
            }

            val direccion = Direccion(calle, num, cp, ciudad)
            //------------------ ········· ------------------

            print("Ingrese su teléfono (puede ingresar varios separados por comas): ")
            val telefonosInput = readlnOrNull().orEmpty()
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            if (telefonosInput.isEmpty() || telefonosInput.any { !isValidTelefono(it) }) {
                println("Error: Uno o más teléfonos no tienen un formato válido. Inténtalo de nuevo.")
                continue
            }

            val usuario = usuarioController.register(email, nombre, nick, Estado.ACTIVO, direccion, telefonosInput)
            usuarioActual = usuario
            return true
        }
    }

    private fun login(): Boolean {
        println("\n--- Inicio de Sesión ---")

        print("Ingrese su email: ")
        val email = readlnOrNull().orEmpty()

        print("Ingrese su apodo: ")
        val apodo = readlnOrNull().orEmpty()

        val usuario = usuarioController.login(email, apodo)

        return if (usuario is Usuario) {
            usuarioActual = usuario
            println("Sesión iniciada como ${usuarioActual?.nombre}")
            true
        } else {
            println("Error en el inicio de sesión")
            false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isValidTelefono(telefono: String): Boolean {
        val phonePattern = "^\\+?\\d{1,4}?[\\s\\-]?\\(?\\d{1,4}?\\)?[\\s\\-]?\\d{1,4}?[\\s\\-]?\\d{1,4}?[\\s\\-]?\\d{1,9}$"
        val pattern = Pattern.compile(phonePattern)
        return pattern.matcher(telefono).matches()
    }
}