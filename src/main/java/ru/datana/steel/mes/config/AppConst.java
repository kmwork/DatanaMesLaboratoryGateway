package ru.datana.steel.mes.config;

import java.nio.charset.Charset;

/**
 * Константы программы
 */
public class AppConst {

    public static final String SYS_DIR_PROP = "app.dir";
    public static final Charset ENCODING = Charset.forName("UTF8");
    public static final String FILE_YAML_PROP = "app.config.file";

    public static final String SCHEMA_INPUT_FILE = "input-xsd-schema.xsd";
    public static final String SUCCESS_LOG_PREFIX = "[App-Успешно] ";
    public static final String ERROR_LOG_PREFIX = "[App-Ошибка] ";
    public static final String APP_LOG_PREFIX = "[App-Danata] ";
}