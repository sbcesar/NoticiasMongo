package org.example.controller

import org.example.entity.Comentario
import org.example.entity.Noticia
import org.example.services.ComentarioService

class ComentarioController(private val comentarioService: ComentarioService) {

    fun listarComentariosPorNoticia(noticia: Noticia): List<Comentario> {
        return comentarioService.listarComentariosPorNoticia(noticia)
    }

    fun agregarComentario(comentario: Comentario): String {
        if (comentario.comentario.isBlank()) {
            return "El contenido del comentario no puede estar vacío"
        }
        return comentarioService.agregarComentario(comentario)
    }
}