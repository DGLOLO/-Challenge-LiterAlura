package Danilo.Challenge_LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Danilo.Challenge_LiterAlura.dto.RespostaApiDTO;
import Danilo.Challenge_LiterAlura.dto.LivroDTO;

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

    public RespostaApiDTO obterDadosMapeados(String endereco) {
        String json = obterDados(endereco);
        try {
            return objectMapper.readValue(json, RespostaApiDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON: " + e.getMessage(), e);
        }
    }

    public List<LivroDTO> obterListaLivros(String endereco) {
        RespostaApiDTO resposta = obterDadosMapeados(endereco);
        return resposta.livros();
    }

    public LivroDTO obterLivro(String endereco) {
        String json = obterDados(endereco);
        try {
            return objectMapper.readValue(json, LivroDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON do livro: " + e.getMessage(), e);
        }
    }
}