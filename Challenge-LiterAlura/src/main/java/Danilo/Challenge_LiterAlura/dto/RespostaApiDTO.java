package Danilo.Challenge_LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaApiDTO(
    @JsonAlias("count") Integer total,
    @JsonAlias("next") String proximaPagina,
    @JsonAlias("previous") String paginaAnterior,
    @JsonAlias("results") List<LivroDTO> livros
) {}

