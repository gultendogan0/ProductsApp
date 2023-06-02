package com.gultendogan.gulten_dogan_odev6.service

import com.gultendogan.gulten_dogan_odev6.model.ApiResponse
import com.gultendogan.gulten_dogan_odev6.model.Product
import com.gultendogan.gulten_dogan_odev6.model.SearchResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @GET("products")
    fun getProducts(@Query("limit") limit: Int): Call<ApiResponse>

    @GET("products/search")
    fun searchProducts(@Query("q") query: String): Call<SearchResult>
}