package com.meuapp.api.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.meuapp.api.MainActivity;
import com.meuapp.api.R;
import com.meuapp.api.config.RetrofitClient;
import com.meuapp.api.model.LoginRequest;
import com.meuapp.api.model.TokenResponse;
import com.meuapp.api.service.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private AuthApiService authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        authApi = RetrofitClient.getRetrofitInstance().create(AuthApiService.class);

        btnLogin.setOnClickListener(v -> {
            Log.d("LOGIN_EVENT", "Botão de login foi clicado!");

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(username, password);

            authApi.login(loginRequest).enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    Log.d("LOGIN_EVENT", "Resposta do servidor: " + response.code());

                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();
                        salvarToken(token);
                        Log.d("LOGIN_EVENT", "Token salvo com sucesso!");

                        Toast.makeText(LoginActivity.this, "Login efetuado!", Toast.LENGTH_SHORT).show();

                        // Redirecionar para MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Finaliza LoginActivity para evitar voltar com botão de voltar
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro de login: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.e("LOGIN_EVENT", "Erro na requisição: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Erro na conexão!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void salvarToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", token);
        editor.apply();
        Log.d("LOGIN_EVENT", "Token armazenado: " + token);
    }
}