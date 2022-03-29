package com.example.firebaserestapi.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.firebaserestapi.data.User;
import com.example.firebaserestapi.network.FirebaseAPI;

import java.io.IOException;
import java.util.List;

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
    MutableLiveData<List<User>> userListMutableLiveData;
    MutableLiveData<User> userMutableLiveData;

    public UserRepository() {
        this.userListMutableLiveData = new MutableLiveData<>();
        //define userlist
        userMutableLiveData = new MutableLiveData<>();

    }

    //get user from firebase
    public MutableLiveData<List<User>> getUserDetailsListMutableLiveData() {
        Log.i("TAG", "getUserDetailsListMutableLiveData: ");
 /*     List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("Hamid");
        user.setEmail("an@gmail.com");
        user.setPhone("888888333");
        userList.add(user);
        User user1 = new User();
        user1.setName("Hamid1");
        user1.setEmail("an@gmail.com1");
        user1.setPhone("8888883331");
        userList.add(user1);
        new Handler().postDelayed(() -> {
            userListMutableLiveData.postValue(userList);
        }, 3000);
*/
        fetchFirebaseUserList();

        return userListMutableLiveData;
    }

    private void fetchFirebaseUserList() {
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
        FirebaseAPI firebaseAPI = retrofit.create(FirebaseAPI.class);

        Call<List<User>> call2 = firebaseAPI.getUserListData();

        call2.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                Log.d("Response ", "onResponse");
                userListMutableLiveData.postValue(response.body());


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("Response ", "onFailure");
                //t1.setText("Notification failure");
            }
        });
    }


    //delete user

    //update user
}