package ru.datana.steel.mes.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * Загрузка application.yml общеее
 */
@Configuration
@ConfigurationProperties(prefix = "datana.global")
@Slf4j
@EnableTransactionManagement
public class SpringConfig {

    @PostConstruct
    protected void postConstruct() {
        log.info("[Safe:CONFIG] APP VERSION = " + AppVersion.getDatanaAppVersion());
    }


}