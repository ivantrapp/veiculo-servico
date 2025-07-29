package com.servico.backend.configuration;

import com.servico.backend.veiculo.VeiculoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPublisher implements RabbitPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publicar(String exchange, String route, VeiculoDto mensagem) {
        rabbitTemplate.convertAndSend(exchange, route, mensagem);
    }
}
