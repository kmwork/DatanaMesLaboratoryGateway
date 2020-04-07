package ru.datana.steel.mes.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Параметры для работы с ApacheMQ
 */
@Getter
@Setter
@Component("testingActiveMqConfigurationProperties")
@ConfigurationProperties(prefix = "safe.activemq")
public class MesActiveMqConfigurationProperties {


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