package com.diljith.whiterabbit.network

import com.diljith.whiterabbit.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
interface ApiInterFace {
    @GET("5d565297300000680030a986")
    fun getTodoListApi(): Call<ApiResponse>
}