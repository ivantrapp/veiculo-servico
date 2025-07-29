package com.servico.backend.modelo;

import com.servico.backend.marca.Marca;
import com.servico.backend.marca.MarcaDto;
import com.servico.backend.marca.MarcaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModeloMapper {

    private final MarcaMapper marcaMapper;

    @Autowired
    public ModeloMapper(MarcaMapper marcaMapper) {
        this.marcaMapper = marcaMapper;
    }

    public Modelo dtoToEntity(ModeloDto modeloDto) {
        return Modelo.builder()
                .id(modeloDto.getId())
                .marca(Marca.builder().id(modeloDto.getMarcaDto().getId()).build())
                .name(modeloDto.getName())
                .fipeId(modeloDto.getIdFipe())
                .build();
    }

    public ModeloDto fabricarModeloDto(String nomeModelo, Integer fipeId, String marcaId) {
        return ModeloDto.builder()
                .name(nomeModelo)
                .idFipe(fipeId)
                .marcaDto(MarcaDto.builder().id(marcaId).build())
                .build();
    }
}
