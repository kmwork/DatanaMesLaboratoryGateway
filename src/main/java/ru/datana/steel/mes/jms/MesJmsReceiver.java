package ru.datana.steel.mes.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.datana.steel.mes.config.AppConst;
import ru.datana.steel.mes.xml.DatanaXmlValidator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Сервис по JMS - точка входа в сервис по IBM-MQ или Apache ActiveMQ
 */
@Component("safeJmsListener")
@Slf4j
public class MesJmsReceiver implements MessageListener {

    private final static String PREFIX_LOG = "[JMS] ";
    private DatanaXmlValidator xmlValidator = DatanaXmlValidator.getInstance();

    @PostConstruct
    protected void postConstruct() {
        log.info(PREFIX_LOG + "Запуск JMS-сервиса.");
    }

    @PreDestroy
    protected void preDestroy() {
        log.info(PREFIX_LOG + "Остановка JMS-сервиса.");
    }

    @Override
    public void onMessage(Message message) {

        String prefix = PREFIX_LOG + "[onMessage]";
        String msg = null;
        String jmsDestination = null;
        try {
            jmsDestination = message.getJMSDestination().toString();

            if (message instanceof TextMessage)
                msg = ((TextMessage) message).getText();
            else
                msg = "WARN: not text message, type message : " + message.getJMSType();

            log.info(prefix + "input message = " + msg);

            DatanaXmlValidator.getInstance();
            xmlValidator.validate(msg);
        } catch (Exception e) {
            String errorMsg = String.format(AppConst.ERROR_LOG_PREFIX, jmsDestination, msg);
            log.error(errorMsg, e);
        }

    }


}
