package ru.datana.steel.mes.xml.pojo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Наименование химического элемента или параметра и результат
 * 
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChemicalCompositionDataType", propOrder = {
    "smeltingNumber",
    "sampleNumber",
    "samplingDatetime",
    "sampleAnalysisDatetime",
    "compositionType",
    "unitName",
    "controlledElementsAnalysisResults"
})
@Data
public class ChemicalCompositionDataType {

    @XmlElement(required = true)
    protected String smeltingNumber;
    @XmlElement(required = true)
    protected String sampleNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar samplingDatetime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sampleAnalysisDatetime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected CompositionTypeType compositionType;
    @XmlElement(required = true)
    protected String unitName;
    @XmlElement(required = true)
    protected ControlledElementsAnalysisResultsType controlledElementsAnalysisResults;

}
