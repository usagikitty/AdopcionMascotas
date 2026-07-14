package com.example.adopcionmascotas.domain.model

data class Pet(
    val id: Long,
    val name: String,
    val breed: String,
    val age: Int,
    val description: String,
    val isAvailable: Boolean
)
