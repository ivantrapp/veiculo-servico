package com.servico.backend.marca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarcaDto {

    private String id;
    private String name;
    @JsonIgnore
    private Integer fipeId;
    @JsonProperty(value = "total_modelos")
    private long totalModelos;
}
