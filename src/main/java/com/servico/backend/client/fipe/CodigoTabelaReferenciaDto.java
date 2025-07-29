package com.servico.backend.client.fipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CodigoTabelaReferenciaDto {
    @JsonProperty(value = "Codigo")
    private int codigo;

    @JsonProperty(value = "Mes")
    private String mes;

    private int ano;
}
