package ru.datana.steel.mes.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Параметры для работы с ApacheMQ
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "datana.activemq")
@Configuration
public class JmsProperties {


    private String brokerUrl;
    /**
     * Очередь ответов в консоль управления
     */
    private String responseQueue;

    /**
     * Очередь для запросов
     */
    private String requestQueue;


}