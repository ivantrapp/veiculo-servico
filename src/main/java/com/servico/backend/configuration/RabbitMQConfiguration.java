package com.servico.backend.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String FILA_CADASTRO_VEICULO = "cadastro.veiculo";
    public static final String FILA_CADASTRO_VEICULO_EXCHANGE = "cadastro.veiculo.exchange";
    public static final String FILA_CADASTRO_VEICULO_ROUTE = "cadastro.veiculo.route";

    @Bean
    public Queue cadastroVeiculoQueue() {
        return new Queue(FILA_CADASTRO_VEICULO, true);
    }

    @Bean
    public DirectExchange cadastroVeiculoExchange() {
        return new DirectExchange(FILA_CADASTRO_VEICULO_EXCHANGE);
    }

    @Bean
    public Binding cadastroVeiculoBind(Queue cadastroVeiculoQueue, DirectExchange cadastroVeiculoExchange) {
        return BindingBuilder
                .bind(cadastroVeiculoQueue)
                .to(cadastroVeiculoExchange)
                .with(FILA_CADASTRO_VEICULO_ROUTE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());

        return factory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
