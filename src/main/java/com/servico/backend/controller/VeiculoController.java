package com.servico.backend.controller;

import com.servico.backend.veiculo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculo")
@Slf4j
public class VeiculoController {

    private final VeiculoComponent veiculoComponent;
    private final VeiculoService veiculoService;
    private final VeiculoMapper veiculoMapper;

    @Autowired
    public VeiculoController(VeiculoComponent veiculoComponent, VeiculoService veiculoService, VeiculoMapper veiculoMapper) {
        this.veiculoComponent = veiculoComponent;
        this.veiculoService = veiculoService;
        this.veiculoMapper = veiculoMapper;
    }

    @PostMapping("/cadastrar")
    private ResponseEntity<String> cadastrarVeiculo(@RequestBody VeiculoDto veiculo) {
        return veiculoComponent.cadastrarVeiculo(veiculo);
    }

    @GetMapping
    private VeiculoDto buscarValorVeiculo(@RequestParam String placa) {
        return veiculoMapper.entityToDto(veiculoService.buscarVeiculoPorPlaca(placa));
    }

    @GetMapping("/veiculos/{marcaId}")
    private Page<VeiculoDto> buscarVeiculosDeUmaMarca(@PathVariable String marcaId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "id") String sort) {
        log.info("Buscando veiculos por marca");
        Pageable paginacao = PageRequest.of(page, size, Sort.by(sort));
        Page<Veiculo> veiculosPaginados = veiculoService.buscarVeiculosPorMarcaPaginado(marcaId, paginacao);

        return veiculosPaginados.map(veiculoMapper::entityToDto);
    }
}
