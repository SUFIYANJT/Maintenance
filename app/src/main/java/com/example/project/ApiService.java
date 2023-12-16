package com.example.project;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/home/{userId}")
    Call<ResponseDjango> getUser(@Path("userId") String userId);
}
