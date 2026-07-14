package com.example.adopcionmascotas.data.remote.dto

data class SolicitudAdopcionRequest(
    val mascotaId: Long,
    val usuarioId: Long? = null // Normalmente el backend lo saca del token, pero lo dejamos por si acaso
)
