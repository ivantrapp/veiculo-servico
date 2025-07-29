package com.servico.backend.veiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;

    @Autowired
    public VeiculoServiceImpl(VeiculoRepository veiculoRepository,
                              VeiculoMapper veiculoMapper) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoMapper = veiculoMapper;
    }

    @Override
    public Veiculo buscarVeiculo(String id) {
        return veiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Registro não encontrado"));
    }

    @Override
    public Veiculo salvar(VeiculoDto veiculoDto) {
        return veiculoRepository.save(veiculoMapper.dtoToEntity(veiculoDto));
    }

    @Override
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    @Override
    public Page<Veiculo> buscarVeiculosPorMarcaPaginado(String marcaId, Pageable pageable) {
        return veiculoRepository.findAllByMarcaId(marcaId, pageable);
    }
}
