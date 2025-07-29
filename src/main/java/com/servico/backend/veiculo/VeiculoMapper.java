package com.servico.backend.veiculo;

import com.servico.backend.marca.Marca;
import com.servico.backend.marca.MarcaMapper;
import com.servico.backend.modelo.Modelo;
import com.servico.backend.modelo.ModeloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    private final ModeloMapper modeloMapper;
    private final MarcaMapper marcaMapper;

    @Autowired
    public VeiculoMapper(ModeloMapper modeloMapper,
                         MarcaMapper marcaMapper) {
        this.modeloMapper = modeloMapper;
        this.marcaMapper = marcaMapper;
    }

    public Veiculo dtoToEntity(VeiculoDto veiculoDto) {
        return Veiculo.builder()
                .dataCadastro(veiculoDto.getDataCadastro())
                .placa(veiculoDto.getPlaca())
                .precoFipe(veiculoDto.getPrecoFipe())
                .precoAnunciado(veiculoDto.getPrecoAnunciado())
                .ano(veiculoDto.getAno())
                .marca(Marca.builder().id(veiculoDto.getMarcaDto().getId()).build())
                .modelo(Modelo.builder().id(veiculoDto.getModeloDto().getId()).build())
                .build();
    }

    public VeiculoDto entityToDto(Veiculo veiculo) {
        return VeiculoDto.builder()
                .id(veiculo.getId())
                .idMarca(veiculo.getMarca().getFipeId())
                .idModelo(veiculo.getModelo().getFipeId())
                .precoAnunciado(veiculo.getPrecoAnunciado())
                .precoFipe(veiculo.getPrecoFipe())
                .placa(veiculo.getPlaca())
                .ano(veiculo.getAno())
                .dataCadastro(veiculo.getDataCadastro())
                .build();
    }
}
