package Danilo.Challenge_LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FormatosDTO(
    @JsonProperty("text/html") String html,
    @JsonProperty("application/epub+zip") String epub,
    @JsonProperty("text/plain; charset=utf-8") String texto,
    @JsonProperty("application/pdf") String pdf
) {}

