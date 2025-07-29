package com.servico.backend.marca;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca dtoToEntity(MarcaDto marcaDto) {
        return Marca.builder()
                .id(marcaDto.getId())
                .name(marcaDto.getName())
                .fipeId(marcaDto.getFipeId())
                .build();
    }

    public MarcaDto fabricarMarcaDto(String nomeMarca, Integer fipeId){
        return MarcaDto.builder()
                .name(nomeMarca)
                .fipeId(fipeId)
                .build();
    }

    public MarcaDto fabricarMarcaDto(Marca marca, long totalModelosPorMarca){
        return MarcaDto.builder()
                .id(marca.getId())
                .name(marca.getName())
                .totalModelos(totalModelosPorMarca)
                .build();
    }
}
