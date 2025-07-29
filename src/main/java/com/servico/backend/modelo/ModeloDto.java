package com.servico.backend.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.servico.backend.marca.MarcaDto;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModeloDto {

    private String id;
    private String name;
    private Integer idFipe;
    private MarcaDto marcaDto;
}
