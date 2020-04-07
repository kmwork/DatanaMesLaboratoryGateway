package ru.datana.steel.mes.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.datana.steel.mes.xml.pojo.ChemicalCompositionDataType;
import ru.sbrf.ais.microservices.safe.model.xml.*;

import javax.annotation.PostConstruct;

/**
 * Загрузка application.yml общеее
 */
@Configuration
@ConfigurationProperties(prefix = "datana.global")
@Slf4j
@EnableTransactionManagement
public class SpringConfig {

    @Getter
    @Setter
    protected String appVersion;

    @PostConstruct
    protected void postConstruct() {
        log.info("[Safe:CONFIG] APP VERSION = " + appVersion);
    }


}