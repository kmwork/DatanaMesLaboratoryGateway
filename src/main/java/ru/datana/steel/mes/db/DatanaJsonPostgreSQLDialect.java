package ru.datana.steel.mes.db;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

/**
 * Диалект по работе PostgreSQL при JSON типах
 *
 * @author timfulmer
 */
public class DatanaJsonPostgreSQLDialect extends PostgreSQL10Dialect {

    private static final String TAG = "jsonb";
    public DatanaJsonPostgreSQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, TAG);
        this.registerColumnType(Types.OTHER, TAG);
        this.registerHibernateType(Types.JAVA_OBJECT, TAG);
        this.registerHibernateType(Types.OTHER, TAG);
    }
}
