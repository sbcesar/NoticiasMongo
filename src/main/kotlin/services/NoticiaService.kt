package org.example.services

import org.example.entity.Noticia
import org.example.entity.Usuario
import org.example.repositories.NoticiaRepository

class NoticiaService(private val noticiaRepository: NoticiaRepository) {

    fun publicarNoticia(noticia: Noticia): String {
        return noticiaRepository.publicarNoticia(noticia)
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<String> {
        return noticiaRepository.listarNoticiasPorUsuario(usuarioNick)
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<String> {
        return noticiaRepository.buscarNoticiasPorEtiquetas(etiquetas)
    }

    fun listarUltimasNoticias(): List<String> {
        return noticiaRepository.listarUltimasNoticias()
    }
}