//package ru.datana.steel.mes;
//
//import lombok.extern.slf4j.Slf4j;
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathFactory;
//import java.io.File;
//import java.io.FileInputStream;
//
//@Slf4j
//public class TestXmlApp {
//    private static String fileName = "/home/lin/work-lanit/DatanaMesGateway-K9/etc/response-example-pg.xml";
//    public static void main(String[] args) throws Exception {
//        File file = new File(fileName);
//        FileInputStream fileIS = new FileInputStream(file);
//        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = builderFactory.newDocumentBuilder();
//        Document xmlDocument = builder.parse(fileIS);
//        XPath xPath = XPathFactory.newInstance().newXPath();
//        String expression = "/MESChemicalCompositionSaveResult/status";
//        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
//        if (nodeList!=null && nodeList.getLength()==1){
//            log.info("value = "+nodeList.item(0).getTextContent());
//        }else log.error ("not status");
//
//    }
//
//
//}
