package com.example.adopcionmascotas.domain.model

data class Pet(
    val id: Int,
    val name: String,
    val breed: String,
    val age: String,
    val imageUrl: String,
    val description: String
)
