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
        String fileName = System.getProperty(AppConst.FILE_YAML_PROP);
        if (StringUtils.isEmpty(fileName)) {
            log.error(AppConst.APP_LOG_PREFIX + "Профиль клиента не указан по свойству =  " + AppConst.FILE_YAML_PROP);
            System.exit(-110);
        }
        ExtSpringProfileUtil.extConfigure(fileName);
        SpringApplication app = new SpringApplication(DevJmsApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
