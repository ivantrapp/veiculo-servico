package com.servico.backend.configuration;

import com.servico.backend.veiculo.VeiculoDto;

public interface RabbitPublisher {

    void publicar(String exchange, String route, VeiculoDto mensagem);
}
