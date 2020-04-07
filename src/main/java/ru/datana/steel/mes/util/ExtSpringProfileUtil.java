package ru.datana.steel.mes.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.datana.steel.mes.config.AppConst;

import java.io.File;
import java.util.Properties;

@Slf4j
public class ExtSpringProfileUtil {

    /**
     * Настройка Spring-приложение из вышесенных во внешнюю папку etc настроек спринга
     *
     * @param springFile файл для YAML настроек
     */
    public static void extConfigure(String springFile) {

        // проверка что указано свойсто app.dir
        String configLocation = System.getProperty(AppConst.SYS_DIR_PROP); //get the default config directory location
        if (StringUtils.isEmpty(configLocation)) {
            log.error(AppConst.APP_LOG_PREFIX + "Путь к настройкам не указан по java.options по имени свойства =  " + AppConst.SYS_DIR_PROP);
            System.exit(-100);
        }

        File configPath = new File(configLocation, springFile);
        log.info(AppConst.APP_LOG_PREFIX + "[Config]: configPath = " + configPath);
        log.info(AppConst.APP_LOG_PREFIX + "Настройка приложения");


        //настройка констант
        if (configPath.exists()) {
            Properties props = System.getProperties();
            props.setProperty("spring.config.location", configPath.getAbsolutePath()); //set the config file to use
            props.setProperty("java.awt.headless", "false");
            props.setProperty("file.encoding", "UTF8");
        } else {
            log.error(AppConst.ERROR_LOG_PREFIX + "Конфиг не найден по пути " + configPath.getAbsolutePath());
            System.exit(-200);
        }
    }

}
