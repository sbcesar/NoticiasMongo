package org.example.controller

import org.example.entity.Noticia
import org.example.entity.Usuario
import org.example.services.NoticiaService

class NoticiaController(private val noticiaService: NoticiaService) {

    fun publicarNoticia(noticia: Noticia): String {
        if (noticia.titulo.isBlank() || noticia.cuerpo.isBlank()) {
            return "El título y contenido son obligatorios"
        }
        return noticiaService.publicarNoticia(noticia)
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<String> {
        return noticiaService.listarNoticiasPorUsuario(usuarioNick)
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<String> {
        if (etiquetas.isEmpty()) {
            return emptyList()
        }
        return noticiaService.buscarNoticiasPorEtiquetas(etiquetas)
    }

    fun listarUltimasNoticias(): List<String> {
        return noticiaService.listarUltimasNoticias()
    }
}