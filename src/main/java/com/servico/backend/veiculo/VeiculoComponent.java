package com.servico.backend.veiculo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servico.backend.client.fipe.AnoModeloVeiculoDto;
import com.servico.backend.client.fipe.CodigoTabelaReferenciaDto;
import com.servico.backend.client.fipe.FipeService;
import com.servico.backend.client.fipe.ValorFipeVeiculoDto;
import com.servico.backend.configuration.RabbitMQConfiguration;
import com.servico.backend.configuration.RabbitPublisher;
import com.servico.backend.exception.VeiculoCadastradoException;
import com.servico.backend.marca.Marca;
import com.servico.backend.marca.MarcaComponent;
import com.servico.backend.marca.MarcaDto;
import com.servico.backend.modelo.Modelo;
import com.servico.backend.modelo.ModeloComponent;
import com.servico.backend.modelo.ModeloDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class VeiculoComponent {

    private final ObjectMapper objectMapper;
    private final RabbitPublisher rabbitPublisher;
    private final FipeService fipeService;
    private final VeiculoService veiculoService;
    private final MarcaComponent marcaComponent;
    private final ModeloComponent modeloComponent;
    private final VeiculoMapper veiculoMapper;

    @Value("${app.fipe-service.referer}")
    private String refererFipe;

    @Value("${app.fipe-service.host}")
    private String host;

    @Autowired
    public VeiculoComponent(ObjectMapper objectMapper,
                            RabbitPublisher rabbitPublisher,
                            FipeService fipeService,
                            VeiculoService veiculoService,
                            MarcaComponent marcaComponent,
                            ModeloComponent modeloComponent,
                            VeiculoMapper veiculoMapper) {
        this.objectMapper = objectMapper;
        this.rabbitPublisher = rabbitPublisher;
        this.fipeService = fipeService;
        this.veiculoService = veiculoService;
        this.marcaComponent = marcaComponent;
        this.modeloComponent = modeloComponent;
        this.veiculoMapper = veiculoMapper;
    }

    public ResponseEntity<String> cadastrarVeiculo(VeiculoDto veiculoDto) {
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(veiculoDto.getPlaca());

        if (!Objects.isNull(veiculo)) throw new VeiculoCadastradoException("Veiculo já cadastrado");

        veiculoDto.setDataCadastro(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        rabbitPublisher.publicar(RabbitMQConfiguration.FILA_CADASTRO_VEICULO_EXCHANGE,
                RabbitMQConfiguration.FILA_CADASTRO_VEICULO_ROUTE,
                veiculoDto);

        return ResponseEntity.ok().body("Adicionado para fila de cadastros");
    }

    public void cadastrarVeiculoAssincrono(VeiculoDto veiculoDto) {
        CodigoTabelaReferenciaDto codigoTabelaReferencia = buscarCodigoTabelaReferencia();

        AnoModeloVeiculoDto anoModeloVeiculo = buscarAnoETipoCombustivelModelo(codigoTabelaReferencia.getCodigo(), veiculoDto);

        ValorFipeVeiculoDto valorFipeVeiculoDto = buscarValorFipeVeiculo(codigoTabelaReferencia.getCodigo(), anoModeloVeiculo, veiculoDto);

        persistirVeiculo(veiculoDto, valorFipeVeiculoDto, anoModeloVeiculo);
    }

    private void persistirVeiculo(VeiculoDto veiculoDto, ValorFipeVeiculoDto valorFipeVeiculoDto, AnoModeloVeiculoDto anoModeloVeiculo) {
        Marca marca = marcaComponent.buscarEsalvarMarca(valorFipeVeiculoDto.getMarca(), veiculoDto.getIdMarca());
        log.info("Persistindo marca");
        Modelo modelo = modeloComponent.buscarEsalvarModelo(valorFipeVeiculoDto.getModelo(), veiculoDto.getIdModelo(), marca.getId());
        log.info("Persistindo modelo");
        veiculoDto.setMarcaDto(MarcaDto.builder().id(marca.getId()).build());
        veiculoDto.setModeloDto(ModeloDto.builder().id(modelo.getId()).build());
        veiculoDto.setPrecoFipe(valorFipeVeiculoDto.formatarPrecoFipe(valorFipeVeiculoDto.getValorFipe()));
        veiculoDto.setAno(anoModeloVeiculo.getAno());

        log.info("Persistindo veículo em banco relacional");
        veiculoService.salvar(veiculoDto);
    }

    private ValorFipeVeiculoDto buscarValorFipeVeiculo(Integer codigoTabelaReferencia, AnoModeloVeiculoDto anoModeloVeiculoDto, VeiculoDto veiculoDto) {
        MultiValueMap<String, Object> header = fabricarHeader();

        MultiValueMap<String, Object> body = fabricarBodyComCodigoMarcaModeloTabelaReferenciaETipoVeiculo(codigoTabelaReferencia, veiculoDto);
        body.add("codigoTipoCombustivel", 1);
        body.add("anoModelo", anoModeloVeiculoDto.getAno());
        body.add("tipoConsulta", "tradicional");

        log.info("Buscando valor veículo tabela fipe");
        return fipeService.buscarValorFipeVeiculo(URI.create(refererFipe + "/api/veiculos/ConsultarValorComTodosParametros"), header, body);
    }

    private AnoModeloVeiculoDto buscarAnoETipoCombustivelModelo(Integer codigoTabelaReferencia, VeiculoDto veiculoDto) {
        MultiValueMap<String, Object> header = fabricarHeader();

        MultiValueMap<String, Object> body = fabricarBodyComCodigoMarcaModeloTabelaReferenciaETipoVeiculo(codigoTabelaReferencia, veiculoDto);

        log.info("Buscando ano e tipo combustível de modelo");
        List<AnoModeloVeiculoDto> listaAnoModeloVeiculos = fipeService.buscarAnoModelo(URI.create(refererFipe + "/api/veiculos/ConsultarAnoModelo"), header, body);

        return buscarAnoModeloRequisitado(listaAnoModeloVeiculos, veiculoDto.getAno());
    }

    private AnoModeloVeiculoDto buscarAnoModeloRequisitado(List<AnoModeloVeiculoDto> listaAnoModeloVeiculos, int ano) {
        if (listaAnoModeloVeiculos.isEmpty()) {
            throw new RuntimeException("Nenhum ano modelo encontrado");
        }

        List<AnoModeloVeiculoDto> anosModelosValidos = listaAnoModeloVeiculos.stream().filter(anoModeloVeiculoDto -> Objects.equals(anoModeloVeiculoDto.getAno(), ano)).toList();

        if (anosModelosValidos.isEmpty())
            throw new RuntimeException("É necessário possuir um ano para o modelo para o ano enviado pelo usuário");

        return anosModelosValidos.getFirst();

    }

    private MultiValueMap<String, Object> fabricarBodyComCodigoMarcaModeloTabelaReferenciaETipoVeiculo(Integer codigoTabelaReferencia, VeiculoDto veiculoDto) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("codigoTabelaReferencia", codigoTabelaReferencia);
        body.add("codigoTipoVeiculo", 1);
        body.add("codigoMarca", veiculoDto.getIdMarca());
        body.add("codigoModelo", veiculoDto.getIdModelo());

        return body;
    }

    private CodigoTabelaReferenciaDto buscarCodigoTabelaReferencia() {
        MultiValueMap<String, Object> header = fabricarHeader();

        log.info("Buscando codigo tabela referencia");
        List<CodigoTabelaReferenciaDto> tabelasReferencias = fipeService.buscarListasDeReferencia(URI.create(refererFipe + "/api/veiculos/ConsultarTabelaDeReferencia"), header, "");

        return tabelasReferencias.getFirst();
    }

    private MultiValueMap<String, Object> fabricarHeader() {
        MultiValueMap<String, Object> header = new LinkedMultiValueMap<>();
        header.add("Referer", refererFipe);
        header.add("Host", host);

        return header;
    }
}
