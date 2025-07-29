package com.servico.backend.veiculo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.servico.backend.marca.MarcaDto;
import com.servico.backend.modelo.ModeloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoDto implements Serializable {
    private String id;
    private Integer idMarca;
    private Integer idModelo;
    private Float precoAnunciado;
    private Float precoFipe;
    private String placa;
    private int ano;
    private String dataCadastro;

    @JsonIgnore
    private MarcaDto marcaDto;
    @JsonIgnore
    private ModeloDto modeloDto;
}
