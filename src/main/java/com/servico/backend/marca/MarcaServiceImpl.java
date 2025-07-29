package com.servico.backend.marca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Autowired
    public MarcaServiceImpl(MarcaRepository marcaRepository,
                            MarcaMapper marcaMapper) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
    }

    @Override
    public Marca buscarMarca(String name) {
        return marcaRepository.findByName(name);
    }

    @Override
    public List<Marca> buscarTodasMarcas() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca salvarMarca(MarcaDto marcaDto) {
        return marcaRepository.save(marcaMapper.dtoToEntity(marcaDto));
    }
}
