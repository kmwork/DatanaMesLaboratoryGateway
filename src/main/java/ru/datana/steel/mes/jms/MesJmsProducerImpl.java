package ru.datana.steel.mes.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MesJmsProducerImpl implements MesJmsProducer{
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${datana.activemq.responseQueue}")
    private String jmsResponseQueue;

    public void sendOnError(String msg){
        jmsTemplate.convertAndSend(msg);
    }
}
