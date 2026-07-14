package com.example.adopcionmascotas.data.remote.dto

data class PetDto(
    val id: Long? = null,
    val nombre: String? = null,
    val especie: String? = null,
    val edad: Int? = null,
    val descripcion: String? = null,
    val disponible: Boolean? = null
)
