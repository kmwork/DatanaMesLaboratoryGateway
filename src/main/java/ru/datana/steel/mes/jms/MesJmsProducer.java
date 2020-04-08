package ru.datana.steel.mes.jms;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("jmsProducer")
public interface MesJmsProducer {
    void sendOnError(String msg);
}
