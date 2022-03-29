package com.example.firebaserestapi.network;

import com.example.firebaserestapi.data.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FirebaseAPI {

    @GET("/user.json")
    Call<JsonObject> getUserListData();

    @POST("/user.json")
    Call<Void> postUserListData(@Body User user);


}
