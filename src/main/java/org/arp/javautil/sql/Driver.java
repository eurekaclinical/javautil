package org.arp.javautil.sql;

/*-
 * #%L
 * JavaUtil
 * %%
 * Copyright (C) 2012 - 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
