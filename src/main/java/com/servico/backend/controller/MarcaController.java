package com.servico.backend.controller;

import com.servico.backend.marca.MarcaComponent;
import com.servico.backend.marca.MarcaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaComponent marcaComponent;

    @Autowired
    public MarcaController(MarcaComponent marcaComponent) {
        this.marcaComponent = marcaComponent;
    }

    @GetMapping
    public List<MarcaDto> buscarMarcasComQuantidadeDeModelos() {
        return marcaComponent.buscarMarcasComQuantidadeDeModelos();
    }
}
