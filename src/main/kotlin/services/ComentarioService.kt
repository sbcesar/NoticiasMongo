package org.example.services

import org.example.entity.Comentario
import org.example.entity.Noticia
import org.example.repositories.ComentarioRepository

class ComentarioService(private val comentarioRepository: ComentarioRepository) {

    fun listarComentariosPorNoticia(noticia: Noticia): List<Comentario> {
        return comentarioRepository.listarComentariosPorNoticia(noticia)
    }

    fun agregarComentario(comentario: Comentario): Comentario {
        return comentarioRepository.agregarComentario(comentario)
    }
}