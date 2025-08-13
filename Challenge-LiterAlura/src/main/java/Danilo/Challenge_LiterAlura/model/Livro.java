package Danilo.Challenge_LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Livro(
    @JsonAlias("id") Long id,
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<Autor> autores,
    @JsonAlias("languages") List<String> idiomas,
    @JsonAlias("download_count") Integer downloads,
    @JsonAlias("formats") Formatos formatos
) {}
