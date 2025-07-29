package com.servico.backend.client.fipe;

import com.servico.backend.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.List;

@FeignClient(value = "fipe-service", url = "http://localhost:8080/", configuration = FeignConfiguration.class)
public interface FipeService {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CodigoTabelaReferenciaDto> buscarListasDeReferencia(URI url, @RequestHeader MultiValueMap<String, Object> header, @RequestBody String body);

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ValorFipeVeiculoDto buscarValorFipeVeiculo(URI url, @RequestHeader MultiValueMap<String, Object> header, @RequestBody MultiValueMap<String, Object> body);

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<AnoModeloVeiculoDto> buscarAnoModelo(URI url, @RequestHeader MultiValueMap<String, Object> header, @RequestBody MultiValueMap<String, Object> body);

}
