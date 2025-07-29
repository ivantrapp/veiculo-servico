package com.servico.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servico.backend.veiculo.VeiculoComponent;
import com.servico.backend.veiculo.VeiculoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VeiculoComponent veiculoComponent;

    @RabbitListener(queues = RabbitMQConfiguration.FILA_CADASTRO_VEICULO)
    public void receive(VeiculoDto veiculoDto) {
        log.info("Iniciando cadastro de veiculo");

        try {
            veiculoComponent.cadastrarVeiculoAssincrono(veiculoDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
