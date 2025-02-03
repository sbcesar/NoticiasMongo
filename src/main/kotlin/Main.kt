package org.example

import com.mongodb.client.MongoDatabase
import org.example.config.DatabaseManager
import org.example.controller.ComentarioController
import org.example.controller.NoticiaController
import org.example.controller.UsuarioController
import org.example.repositories.ComentarioRepository
import org.example.repositories.NoticiaRepository
import org.example.repositories.UsuarioRepository
import org.example.services.ComentarioService
import org.example.services.NoticiaService
import org.example.services.UsuarioService
import org.example.utils.Menu

fun main() {

    // DataBase
    val databaseManager = DatabaseManager.getInstance()
    val database = databaseManager.connect()

    // Usuario
    val usuarioRepository = UsuarioRepository(database)
    val usuarioService = UsuarioService(usuarioRepository)
    val usuarioController = UsuarioController(usuarioService)

    // Noticia
    val noticiaRepository = NoticiaRepository(database)
    val noticiaService = NoticiaService(noticiaRepository)
    val noticiaController = NoticiaController(noticiaService)

    // Comentario
    val comentarioRepository = ComentarioRepository(database)
    val comentarioService = ComentarioService(comentarioRepository)
    val comentarioController = ComentarioController(comentarioService)

    // Menú
    val menu = Menu(usuarioController, noticiaController, comentarioController)

    // Ejecucución
    try {
        menu.showAuthMenu()
    }catch (e: Exception){
        println("Error en la ejecución: ${e.message}")
    } finally {
        databaseManager.disconnect()
    }
}