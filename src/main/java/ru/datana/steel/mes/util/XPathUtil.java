package ru.datana.steel.mes.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.datana.steel.mes.config.AppConst;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
public class XPathUtil {
    private static final String XPATH_FOR_RESPONSE_STATUS = "/MESChemicalCompositionSaveResult/status";
    private static final String XPATH_FOR_REQUEST_ID = "/MESChemicalCompositionSave/messageID";
    private XPathExpression xPathForId;
    private XPathExpression xPathForStatus;

    @Getter
    private static final XPathUtil instance = new XPathUtil();

    private XPathUtil() {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            xPathForStatus = xPath.compile(XPATH_FOR_RESPONSE_STATUS);
            xPathForId = xPath.compile(XPATH_FOR_REQUEST_ID);
        } catch (XPathExpressionException e) {
            log.error("Ошибка инциализации класса " + this.getClass().getName(), e);
            System.exit(-200);
        }
    }


    public @Nullable
    String getAsTextByXPath(@NonNull String xmlAsString, @NonNull XPathExpression xpath) {
        String result = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlAsString.getBytes(AppConst.ENCODING));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(inputStream);
            NodeList nodeList = (NodeList) xpath.evaluate(xmlDocument, XPathConstants.NODESET);
            if (nodeList != null && nodeList.getLength() == 1) {
                result = nodeList.item(0).getTextContent();
            } else log.error(AppConst.ERROR_LOG_PREFIX + "Нет значения по XPATH");
        } catch (Exception ex) {
            log.error(AppConst.ERROR_LOG_PREFIX + "Ошибка при XPATH разбора по XPATH для xml = " + xmlAsString);
        }
        if (result != null)
            result = result.trim();
        return result;
    }

    public @Nullable
    String getStatusOfResponse(@NonNull String xmlAsString) {
        String result = getAsTextByXPath(xmlAsString, xPathForStatus);
        if (result == null)
            log.error(AppConst.ERROR_LOG_PREFIX + "В ответе хранимки нет поле статуса по xpath = " + xPathForStatus);
        return result;
    }

    public @Nullable
    String getIdByRequest(@NonNull String xmlAsString) {
        String result = getAsTextByXPath(xmlAsString, xPathForId);
        if (result == null)
            log.error(AppConst.ERROR_LOG_PREFIX + "В запросе нет поля ID по xpath = " + XPATH_FOR_REQUEST_ID);
        return result;
    }

}
