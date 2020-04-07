package ru.datana.steel.mes;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

@Slf4j
public class DatanaXmlValidatorApp {
    //private final String xmlFileName = "xml-test.xml";
    private final String xmlFileName = "gen-xml.xml";
    public final String schemaFileName = "input-xsd-schema.xsd";
    private static final String dir = "/home/lin/work-lanit/DatanaMesGateway-K9/etc";
    private static final Charset ENCODING = Charset.forName("UTF8");

    public static void main(String[] args) {
        DatanaXmlValidatorApp app = new DatanaXmlValidatorApp();

        app.run();


    }

    private void run() {
        File xmlFile = new File(dir, xmlFileName);
        File schemaFile = new File(dir, schemaFileName);
        boolean result = validate(xmlFile, schemaFile);
        log.info("File = {} , validation = {}", xmlFile.getAbsolutePath(), result);
    }

    private boolean validate(File xmlFile, File schemaFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (Reader schemaReader = new FileReader(schemaFile, ENCODING);
             Reader xmlReader = new FileReader(xmlFile, ENCODING)) {

            StreamSource streamStreamSource = new StreamSource(schemaReader);
            Schema schema = schemaFactory.newSchema(streamStreamSource);
            StreamSource xmlSource = new StreamSource(xmlReader);

            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}