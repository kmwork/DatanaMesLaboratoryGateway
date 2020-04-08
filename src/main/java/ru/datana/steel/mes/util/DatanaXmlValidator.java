package ru.datana.steel.mes.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import ru.datana.steel.mes.config.AppConst;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

@Slf4j
public class DatanaXmlValidator {
    private static final String PREFIX_LOG = "[XML-Validator] ";
    private Validator validator;
    @Getter
    private final static DatanaXmlValidator instance = new DatanaXmlValidator();

    private DatanaXmlValidator() {
        loadSchema();
    }


    private void loadSchema() {
        log.info(PREFIX_LOG + "Чтение xsd схемы");
        String dir = System.getProperty(AppConst.SYS_DIR_PROP); //get the default config directory location
        File schemaFile = new File(dir, AppConst.SCHEMA_INPUT_FILE);
        log.info("[Схема] schemaFile = " + schemaFile);
        if (!schemaFile.exists()) {
            log.error(AppConst.ERROR_LOG_PREFIX + "Файл '{}' для валидации xsd-схем не найден", schemaFile.getAbsolutePath());
            System.exit(-100);
        }
        try (Reader schemaReader = new FileReader(schemaFile, AppConst.ENCODING)) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            StreamSource streamStreamSource = new StreamSource(schemaReader);
            Schema schema = schemaFactory.newSchema(streamStreamSource);
            validator = schema.newValidator();
        } catch (Exception e) {
            log.error(AppConst.ERROR_LOG_PREFIX + "Ошибка чтения файла xsd-schema = '{}'", schemaFile.getAbsolutePath());
            System.exit(-115);
        }
    }


    /**
     * Валидация XML как строка
     *
     * @param xmlAsString
     * @return null или текст ошибки
     */
    public String validate(@NonNull String xmlAsString) {
        String errorMsg;
        try {
            Reader xmlReader = new StringReader(xmlAsString);
            StreamSource xmlSource = new StreamSource(xmlReader);
            validator.validate(xmlSource);
            errorMsg = null;
        } catch (IOException ioEx) {
            errorMsg = "IOException: Ошибка при чтеннии сообщения xmlSource = " + xmlAsString;
            log.warn(AppConst.ERROR_LOG_PREFIX + errorMsg, ioEx);
        } catch (SAXException saxEx) {
            errorMsg = "SAXException: Не валиный xml: " + saxEx.getLocalizedMessage();
            log.error(AppConst.ERROR_LOG_PREFIX + errorMsg);
        }
        return errorMsg;
    }

}