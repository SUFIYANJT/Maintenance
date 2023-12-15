package com.example.project;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/home/{userId}")
    Call<User> getUser(@Path("userId") String userId);
}
