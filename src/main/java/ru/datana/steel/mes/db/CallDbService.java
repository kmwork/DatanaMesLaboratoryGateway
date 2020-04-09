package ru.datana.steel.mes.db;

import lombok.NonNull;

import java.sql.SQLException;

/**
 * API для вызова хранимок на PostgreSQL
 */
public interface CallDbService {
    /**
     * Сохранить через передачу JSON строки с данными дачиков в базу данных Postgresql
     *
     * @param fromXml
     * @return
     * @throws SQLException
     */
    String dbSave(@NonNull String fromXml);
}
