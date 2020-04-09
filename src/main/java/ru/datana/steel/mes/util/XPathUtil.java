package ru.datana.steel.mes.util;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.datana.steel.mes.config.AppConst;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
@Slf4j
public class XPathUtil {
    private static final String XPATH_FOR_STATUS = "/MESChemicalCompositionSaveResult/status";
    public static String getStatusOfResponse(String xmlAsString){
        String result = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlAsString.getBytes(AppConst.ENCODING));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(inputStream);
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile(XPATH_FOR_STATUS).evaluate(xmlDocument, XPathConstants.NODESET);
            if (nodeList != null && nodeList.getLength() == 1) {
                result = nodeList.item(0).getTextContent();
            } else log.error("not status");
        }catch (Exception ex){
            log.error ("Ошибка при XPATH разбора для чтения статуса в сообщении от хранимки save, текст ="+xmlAsString);
        }
        return result;
    }

}
