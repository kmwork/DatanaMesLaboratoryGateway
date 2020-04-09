package ru.datana.steel.mes.jms;

import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Продюсер для отправки JMS сообщений
 */
@Component("jmsProducer")
public interface MesJmsProducer {
    void sendOnError(@NonNull String msg);
    void sendOnSuccess(@NonNull String msg);
}
