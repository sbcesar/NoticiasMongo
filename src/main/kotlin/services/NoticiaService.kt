package org.example.services

import org.example.entity.Noticia
import org.example.entity.Usuario
import org.example.repositories.NoticiaRepository
import java.util.*

class NoticiaService(private val noticiaRepository: NoticiaRepository) {

    fun publicarNoticia(noticia: Noticia): Noticia {
        return noticiaRepository.publicarNoticia(noticia)
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<Noticia> {
        return noticiaRepository.listarNoticiasPorUsuario(usuarioNick)
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<Noticia> {
        return noticiaRepository.buscarNoticiasPorEtiquetas(etiquetas)
    }

    fun buscarNoticiaPorTitulo(titulo: String): List<Noticia> {
        return noticiaRepository.buscarNoticiaPorTitulo(titulo)
    }

    fun listarTodas(): List<Noticia> {
        return noticiaRepository.listarTodas()
    }

    fun listarUltimasNoticias(): List<Noticia> {
        return noticiaRepository.listarUltimasNoticias()
    }
}