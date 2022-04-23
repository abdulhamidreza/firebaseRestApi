package com.example.firebaserestapi.network

import com.example.firebaserestapi.data.User
import com.example.firebaserestapi.data.UserKt
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitServiceKt {

    @GET("/user.json")
    suspend fun getUserListData(): Response<List<UserKt>>

    @POST("/user.json")
    suspend fun postUserListData(@Body user: User): Response<Void>

    companion object {
        var retrofitServiceKt: RetrofitServiceKt? = null
        fun getInstance(): RetrofitServiceKt {
            if (retrofitServiceKt == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fir-restapi-11c09-default-rtdb.firebaseio.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitServiceKt = retrofit.create(RetrofitServiceKt::class.java)
            }
            return retrofitServiceKt!!
        }

    }
}