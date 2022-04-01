package com.example.firebaserestapi.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.network.FirebaseAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    MutableLiveData<Set<Map.Entry<String, JsonElement>>> userListMutableLiveData;
    MutableLiveData<Boolean> saveUserMutableLiveData;

    public UserRepository() {
        this.userListMutableLiveData = new MutableLiveData<>();
    }

    //get user from firebase
    public void getUserDetailsListMutableLiveData(MutableLiveData<Set<Map.Entry<String, JsonElement>>> userListMutableLiveData) {
        Log.i("TAG", "getUserDetailsListMutableLiveData: ");
        this.userListMutableLiveData = userListMutableLiveData;
        fetchFirebaseUserList();

    }

    private void fetchFirebaseUserList() {

        Call<JsonObject> call = getRetrofitObject().getUserListData();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Set<Map.Entry<String, JsonElement>> s = response.body().entrySet();

                Log.d("Response ", "onResponse");
                userListMutableLiveData.postValue(s);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Response ", "onFailure");
                t.printStackTrace();
            }
        });
    }


    //get user from firebase
    public void saveUserDetailsMutableLiveData(User user, MutableLiveData<Boolean> saveUserMutableLiveData) {
        Log.i("TAG", "saveUserDetailsMutableLiveData: ");
        this.saveUserMutableLiveData = saveUserMutableLiveData;
        saveFirebaseUser(user);
    }


    private void saveFirebaseUser(User user) {

        Call<Void> call2 = getRetrofitObject().postUserListData(user);

        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                saveUserMutableLiveData.postValue(true);
                Log.d("Response ", "onResponse");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Response ", "onFailure");
                saveUserMutableLiveData.postValue(false);
            }
        });
    }

    private FirebaseAPI getRetrofitObject() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                //   .header("Authorization", authtoken); // <-- in test mode
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fir-restapi-11c09-default-rtdb.firebaseio.com")//url of firebase database app
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//use for convert JSON file into object
                .build();

        // prepare call in Retrofit 2.0
        return retrofit.create(FirebaseAPI.class);

    }


    //delete user

    //update user
}