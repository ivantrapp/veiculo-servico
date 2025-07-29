package com.servico.backend.marca;

import java.util.List;

public interface MarcaService {

    Marca buscarMarca(String name);

    List<Marca> buscarTodasMarcas();

    Marca salvarMarca(MarcaDto marcaDto);
}
