package com.servico.backend.client.fipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValorFipeVeiculoDto {
    @JsonProperty(value = "Valor")
    private String valorFipe;
    @JsonProperty(value = "Marca")
    private String marca;
    @JsonProperty(value = "Modelo")
    private String modelo;


    public Float formatarPrecoFipe(String valorFipe) {
        String valorLimpo = valorFipe.replaceAll("[R$\\s]", "")  // remove "R$" e espaços
                .replaceAll("\\.", "")       // remove ponto de milhar
                .replace(",", ".");  // troca vírgula por ponto
        return Float.valueOf(valorLimpo);
    }
}
