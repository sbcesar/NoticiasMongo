package org.example.controller

import org.example.entity.Comentario
import org.example.entity.Noticia
import org.example.services.ComentarioService
import java.util.*

class ComentarioController(private val comentarioService: ComentarioService) {

    fun listarComentariosPorNoticia(noticia: Noticia): List<Comentario> {
        return comentarioService.listarComentariosPorNoticia(noticia)
    }

    fun agregarComentario(comentario: Comentario): Comentario {
        if (comentario.comentario.isBlank()) {
            throw Exception("El contenido del comentario no puede estar vacío")
        }

        val fechaActual = Date()
        if (comentario.fecha.before(fechaActual)) {
            throw Exception("La fecha del comentario no puede ser anterior a la fecha actual")
        }

        return comentarioService.agregarComentario(comentario)
    }
}