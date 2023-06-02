package com.gultendogan.gulten_dogan_odev6.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("products") val products: List<Product>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)