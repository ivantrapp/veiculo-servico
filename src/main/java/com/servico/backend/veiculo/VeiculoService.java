package com.servico.backend.veiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VeiculoService {

    Veiculo buscarVeiculo(String id);

    Veiculo salvar(VeiculoDto veiculoDto);

    Veiculo buscarVeiculoPorPlaca(String placa);

    Page<Veiculo> buscarVeiculosPorMarcaPaginado(String marcaId, Pageable pageable);
}
