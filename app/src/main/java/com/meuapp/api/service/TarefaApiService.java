package com.meuapp.api.service;

import com.meuapp.api.model.Tarefa;
import com.meuapp.api.model.TarefaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TarefaApiService {

    @GET("/tarefas")
    Call<List<Tarefa>> buscarTodasTarefas();

    @POST("/tarefas")
    Call<Tarefa> criarTarefa(@Body Tarefa tarefa);



    @GET("/tarefas")
    Call<List<TarefaDTO>> listarTarefas(@Header("Authorization") String token);

    @POST("/tarefas")
    Call<TarefaDTO> criarTarefa(@Header("Authorization") String token, @Body TarefaDTO tarefa);

    @PUT("/tarefas/{id}")
    Call<TarefaDTO> atualizarTarefa(@Header("Authorization") String token, @Path("id") Long id, @Body TarefaDTO tarefa);

    @DELETE("/tarefas/{id}")
    Call<Void> deletarTarefa(@Header("Authorization") String token, @Path("id") Long id);
}
