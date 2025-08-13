package Danilo.Challenge_LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import Danilo.Challenge_LiterAlura.model.Livro;
import Danilo.Challenge_LiterAlura.model.RespostaApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ConsumoApi {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer requisição HTTP: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Requisição interrompida: " + e.getMessage(), e);
        }

        return response.body();
    }

    public RespostaApi obterDadosMapeados(String endereco) {
        String json = obterDados(endereco);
        try {
            return objectMapper.readValue(json, RespostaApi.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }
    }

    public List<Livro> obterListaLivros(String endereco) {
        RespostaApi resposta = obterDadosMapeados(endereco);
        return resposta.livros();
    }

    public Livro obterLivro(String endereco) {
        String json = obterDados(endereco);
        try {
            return objectMapper.readValue(json, Livro.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON do livro: " + e.getMessage(), e);
        }
    }
}