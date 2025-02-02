package org.example.entity

import org.bson.codecs.pojo.annotations.BsonProperty

data class Cliente(
    val _id: String?, // EMAIL
    val nombre: String,
    val nick: String,
    val estado: Boolean,
    @BsonProperty("telefonos")
    val tlfn: List<String>,
    val direccion: Direccion?
)