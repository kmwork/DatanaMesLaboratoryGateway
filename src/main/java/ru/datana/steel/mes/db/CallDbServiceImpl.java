package ru.datana.steel.mes.db;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;


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
        log.trace("[SQL: Save] pgNativeSaveSQL = " + pgNativeSaveSQL);
    }

    @Override
    public String dbSave(String fromJson) throws SQLException {
        log.trace("[SQL:Save] data = " + fromJson);
        Query funcSave = entityManager.createNativeQuery(pgNativeSaveSQL);
        funcSave.setParameter("fromJson", fromJson);
        String toJson = funcSave.getResultList().get(0).toString();
        log.trace("[SQL:Save] результат = " + toJson);
        return toJson;
    }
}
