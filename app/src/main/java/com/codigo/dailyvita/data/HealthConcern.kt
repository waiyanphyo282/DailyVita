package com.codigo.dailyvita.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HealthConcernList(
    @Expose
    @SerializedName("data")
    val data: List<HealthConcern>
)

data class HealthConcern(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    val checked: Boolean = false,
)

fun List<HealthConcern>.toHealthConcernList(): HealthConcernList {
    return HealthConcernList(data = this)
}