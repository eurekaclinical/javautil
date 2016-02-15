package org.arp.javautil.sql;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author Andrew Post
 */
public enum DatabaseProduct {
    ORACLE,
    MYSQL,
    POSTGRESQL,
    H2,
    OTHER;

    public static DatabaseProduct fromMetaData(DatabaseMetaData metaData) throws SQLException {
        switch (metaData.getDatabaseProductName()) {
            case "PostgreSQL":
                return POSTGRESQL;
            case "Oracle":
                return ORACLE;
            case "MySQL":
                return MYSQL;
            case "H2":
                return H2;
            default:
                return OTHER;
        }
    }
}
