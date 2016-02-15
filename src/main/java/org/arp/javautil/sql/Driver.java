package org.arp.javautil.sql;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author Andrew Post
 */
public enum Driver {
    ORACLE,
    MYSQL,
    POSTGRESQL,
    H2,
    OTHER;

    public static Driver fromMetaData(DatabaseMetaData metaData) throws SQLException {
        switch (metaData.getDriverName()) {
            case "PostgreSQL Native Driver":
                return POSTGRESQL;
            case "Oracle JDBC driver":
                return ORACLE;
            case "MySQL-AB JDBC Driver":
                return MYSQL;
            case "H2 JDBC Driver":
                return H2;
            default:
                return OTHER;
        }
    }
}
