package ru.datana.steel.mes;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import ru.datana.steel.mes.config.AppConst;
import ru.datana.steel.mes.util.ExtSpringProfileUtil;

/**
 * Тестове приложение для ActiveMQ (внешний брокер) + Oracle СУБД
 */
@SpringBootApplication
@EnableJms
@Slf4j
public class DevJmsApp {
    public static void main(String[] args) {
        ExtSpringProfileUtil.extConfigure(AppConst.FILE_YAML);
        SpringApplication app = new SpringApplication(DevJmsApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
