package ru.datana.steel.mes.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
public class MesJmsReceiver {
    @Component
    public class Receiver {

        @JmsListener(destination = "mailbox", containerFactory = "myFactory")
        public void receiveMessage(String  xmlMessage) {
            log.info("[JMS:RECEIVER] message =" + xmlMessage);
        }

    }
