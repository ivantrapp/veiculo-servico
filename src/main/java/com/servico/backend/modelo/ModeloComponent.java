package com.servico.backend.modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ModeloComponent {

    private final ModeloService modeloService;
    private final ModeloMapper modeloMapper;

    @Autowired
    public ModeloComponent(ModeloService modeloService, ModeloMapper modeloMapper) {
        this.modeloService = modeloService;
        this.modeloMapper = modeloMapper;
    }

    public Modelo buscarEsalvarModelo(String nomeModelo, Integer fipeId, String marcaId) {
        Modelo modelo = modeloService.buscarModelo(fipeId);

        if (Objects.isNull(modelo)) {
            return modeloService.salvarModelo(modeloMapper.fabricarModeloDto(nomeModelo, fipeId, marcaId));
        }

        return modelo;
    }
}
