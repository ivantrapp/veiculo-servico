package com.servico.backend.modelo;

public interface ModeloService {

    Modelo buscarModelo(int fipeId);

    Modelo salvarModelo(ModeloDto modeloDto);

    long contarModelosPorMarcaId(String marcaId);
}
