//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.07 at 10:46:47 AM MSK 
//


package ru.datana.steel.mes.xml.pojo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ControlledElementNameType")
@XmlEnum
public enum ControlledElementNameType {


    /**
     * Углерод
     * 
     */
    C("C"),

    /**
     * Кремний
     * 
     */
    SI("SI"),

    /**
     * Молибден
     * 
     */
    MO("MO"),

    /**
     * Титан
     * 
     */
    TI("TI"),

    /**
     * Ванадий
     * 
     */
    V("V"),

    /**
     * Ниобий
     * 
     */
    NB("NB"),

    /**
     * Бор
     * 
     */
    B("B"),

    /**
     * Азот
     * 
     */
    N("N"),

    /**
     * Кальций
     * 
     */
    CA("CA"),

    /**
     * Водород
     * 
     */
    H("H"),

    /**
     * Кислород
     * 
     */
    O("O"),

    /**
     * Барий
     * 
     */
    BA("BA"),

    /**
     * Марганец
     * 
     */
    MN("MN"),

    /**
     * Олово
     * 
     */
    SN("SN"),

    /**
     * Свинец
     * 
     */
    PB("PB"),

    /**
     * Кобальт
     * 
     */
    CO("CO"),

    /**
     * Мышьяк
     * 
     */
    AS("AS"),

    /**
     * Цирконий
     * 
     */
    ZR("ZR"),

    /**
     * Вольфрам
     * 
     */
    W("W"),

    /**
     * Висмут
     * 
     */
    BI("BI"),

    /**
     * Сурьма
     * 
     */
    SB("SB"),

    /**
     * Цинк
     * 
     */
    ZN("ZN"),

    /**
     * Магний
     * 
     */
    MG("MG"),

    /**
     * Церий
     * 
     */
    CE("CE"),

    /**
     * Тантал
     * 
     */
    TA("TA"),

    /**
     * Теллур
     * 
     */
    TE("TE"),

    /**
     * Железо
     * 
     */
    FE("FE"),

    /**
     * Оксид кальция
     * 
     */
    CAO("CAO"),

    /**
     * Оксид алюминия
     * 
     */
    @XmlEnumValue("AL2O3")
    AL_2_O_3("AL2O3"),

    /**
     * Оксид кремния
     * 
     */
    @XmlEnumValue("SIO2")
    SIO_2("SIO2"),

    /**
     * Оксид марганца
     * 
     */
    MNO("MNO"),

    /**
     * Оксиды железа
     * 
     */
    FEON("FEON"),

    /**
     * Оксид магния (жженая магнезия)
     * 
     */
    MGO("MGO"),

    /**
     * Сера
     * 
     */
    S("S"),

    /**
     * Фторид кальция
     * 
     */
    @XmlEnumValue("CAF2")
    CAF_2("CAF2"),

    /**
     * Оксид фосфора (V)
     * 
     */
    @XmlEnumValue("P2O5")
    P_2_O_5("P2O5"),

    /**
     * Оксид хрома (III)
     * 
     */
    @XmlEnumValue("CR2O3")
    CR_2_O_3("CR2O3"),

    /**
     * Оксид титана (IV)
     * 
     */
    @XmlEnumValue("TIAO2")
    TIAO_2("TIAO2"),

    /**
     * Сера (шлак)
     * 
     */
    S_SLG("S_SLG"),

    /**
     * Прочее
     * 
     */
    OTHER("OTHER"),

    /**
     * Алюминий
     * 
     */
    AL("AL"),

    /**
     * УТОЧНИТЬ
     * 
     */
    ALT("ALT"),

    /**
     * Медь
     * 
     */
    CU("CU"),

    /**
     * Хром
     * 
     */
    CR("CR"),

    /**
     * Никель
     * 
     */
    NI("NI"),

    /**
     * Основность
     * 
     */
    BASICITY("BASICITY"),

    /**
     * Температура
     * 
     */
    TEMPERATURE("TEMPERATURE"),

    /**
     * Окисленность
     * 
     */
    OXYDATION("OXYDATION");
    private final String value;

    ControlledElementNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ControlledElementNameType fromValue(String v) {
        for (ControlledElementNameType c: ControlledElementNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
