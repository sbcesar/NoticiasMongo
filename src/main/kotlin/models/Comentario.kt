package org.example.entity

import java.util.*

data class Comentario(
    val usuario: Usuario,
    val noticia: Noticia,
    val comentario: String,
    val fecha: Date
)