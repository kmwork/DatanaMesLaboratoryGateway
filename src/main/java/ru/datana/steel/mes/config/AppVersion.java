package ru.datana.steel.mes.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class AppVersion {
    @Getter
    private static String datanaAppVersion;

    static {
        load();
    }

    private static void load() {
        InputStream is = AppVersion.class.getResourceAsStream(AppConst.RESOURCE_FILE_NAME);
        Properties p = new Properties();
        try {
            p.load(is);
            datanaAppVersion = p.getProperty("datana.app.version", "<Zero version>");
        } catch (IOException e) {
            log.error("Ошибка чтения файла {} из ресурсов", AppConst.RESOURCE_FILE_NAME, e);
            System.exit(-120);
        }
    }
}
