package com.example.shop;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue orderQueue() {
        return new Queue("order.queue", true);
    }

    @Bean
    DirectExchange orderDirectExchange() {
        return new DirectExchange("order.direct.exchange");
    }

    @Bean
    Binding orderBinding(Queue orderQueue, DirectExchange orderDirectExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirectExchange)
                .with("order.queue");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
