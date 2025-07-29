package com.servico.backend.client.fipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnoModeloVeiculoDto {
    @JsonProperty(value = "Label")
    private String label;
    @JsonProperty(value = "Value")
    private String AnoComTipoCombustivel;

    public Integer getAno() {
        return Integer.parseInt(this.AnoComTipoCombustivel.substring(0, 4));
    }

    public Integer getCodigoTipoCombustivel() {
        return Integer.parseInt(this.AnoComTipoCombustivel.substring(5, 6));
    }
}
