package com.servico.backend.modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloServiceImpl implements ModeloService {

    private final ModeloRepository modeloRepository;
    private final ModeloMapper modeloMapper;

    @Autowired
    public ModeloServiceImpl(ModeloRepository modeloRepository,
                             ModeloMapper modeloMapper) {
        this.modeloRepository = modeloRepository;
        this.modeloMapper = modeloMapper;
    }

    @Override
    public Modelo buscarModelo(int fipeId) {
        return modeloRepository.findByFipeId(fipeId);
    }

    @Override
    public Modelo salvarModelo(ModeloDto modeloDto) {
        return modeloRepository.save(modeloMapper.dtoToEntity(modeloDto));
    }

    @Override
    public long contarModelosPorMarcaId(String marcaId) {
        return modeloRepository.countByMarcaId(marcaId);
    }
}
