package com.codigo.dailyvita.data


data class Allergies(
    val data: List<Allergy>
)

data class Allergy(
    val id: Int,
    val name: String,
)