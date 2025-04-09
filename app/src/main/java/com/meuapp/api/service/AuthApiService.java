package com.meuapp.api.service;

import com.meuapp.api.model.LoginRequest;
import com.meuapp.api.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);
}
