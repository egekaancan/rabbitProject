package com.example.pizza.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStatusMQConfig {

    public static final String ORDER_STATUS_EXCHANGE = "status-exchange";

    public static final String ORDER_STATUS_QUEUE = "status-queue";

    public static final String ORDER_STATUS_ROUTING_KEY = "status-routing";

    @Bean
    public Queue orderStatusQueue(){
        return new Queue(ORDER_STATUS_QUEUE);
    }

    @Bean
    public TopicExchange orderStatusExchange() {
        return new TopicExchange(ORDER_STATUS_EXCHANGE);
    }

    @Bean
    public Binding orderStatusBinding(Queue orderStatusQueue, TopicExchange orderStatusExchange) {
        return BindingBuilder.bind(orderStatusQueue).to(orderStatusExchange).with(ORDER_STATUS_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter orderStatusMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate orderStatusTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(orderStatusMessageConverter());
        return rabbitTemplate;
    }
}
