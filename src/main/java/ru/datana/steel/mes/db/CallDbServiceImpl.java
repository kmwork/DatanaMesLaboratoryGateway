package ru.datana.steel.mes.db;

import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Service
@Slf4j
public class CallDbServiceImpl implements CallDbService {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Хранимка на сохранение данных в базе данных PostgreSQL
     */
    @Value("${datana.database-options.postgresql-save-function}")
    @Setter
    private String pgNativeSaveSQL;

    @PostConstruct
    private void init() {
        log.debug("[SQL:Init:Save] pgNativeSaveSQL = " + pgNativeSaveSQL);
    }

    @Override
    public String dbSave(@NonNull String fromXml) {
        log.debug("[SQL:Save] data = " + fromXml);
        Query funcSave = entityManager.createNativeQuery(pgNativeSaveSQL);
        funcSave.setParameter("fromXml", fromXml);
        String toXml = funcSave.getResultList().get(0).toString();
        log.info("[SQL:Save] результат = " + toXml);
        return toXml;
    }
}
