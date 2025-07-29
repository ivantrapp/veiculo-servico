package com.servico.backend.marca;

import com.servico.backend.modelo.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MarcaComponent {

    private final MarcaService marcaService;
    private final MarcaMapper marcaMapper;
    private final ModeloService modeloService;

    @Autowired
    public MarcaComponent(MarcaService marcaService, MarcaMapper marcaMapper, ModeloService modeloService) {
        this.marcaService = marcaService;
        this.marcaMapper = marcaMapper;
        this.modeloService = modeloService;
    }

    public Marca buscarEsalvarMarca(String nomeMarca, Integer fipeId) {
        Marca marca = marcaService.buscarMarca(nomeMarca);

        if (Objects.isNull(marca)) {

            return marcaService.salvarMarca(marcaMapper.fabricarMarcaDto(nomeMarca, fipeId));
        }

        return marca;
    }

    public List<MarcaDto> buscarMarcasComQuantidadeDeModelos() {
        List<Marca> marcas = marcaService.buscarTodasMarcas();

        return fabricarMarcaDtoComInformacoesSobreModelos(marcas);
    }

    private List<MarcaDto> fabricarMarcaDtoComInformacoesSobreModelos(List<Marca> marcas) {
        return marcas.stream().map(marca -> marcaMapper.fabricarMarcaDto(marca, modeloService.contarModelosPorMarcaId(marca.getId()))).toList();
    }
}
