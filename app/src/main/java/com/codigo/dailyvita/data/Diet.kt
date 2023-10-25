package com.codigo.dailyvita.data

import com.google.gson.annotations.Expose

data class Diet(
    @Expose
    val id: Int,
    @Expose
    val name: String,
    @Expose
    val tool_tip: String,
    var checked: Boolean = false,
)

data class DietList(
    val data: List<Diet>
)
