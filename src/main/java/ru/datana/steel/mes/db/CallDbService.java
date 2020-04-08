package ru.datana.steel.mes.db;

import java.sql.SQLException;

/**
 * API для вызова хранимок на PostgreSQL
 */
public interface CallDbService {
    /**
     * Сохранить через передачу JSON строки с данными дачиков в базу данных Postgresql
     *
     * @param fromJson
     * @return
     * @throws SQLException
     */
    String dbSave(String fromJson) throws SQLException;
}
