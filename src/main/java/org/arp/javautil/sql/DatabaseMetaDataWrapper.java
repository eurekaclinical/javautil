package org.arp.javautil.sql;

/*
 * #%L
 * JavaUtil
 * %%
 * Copyright (C) 2012 - 2015 Emory University
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
import org.arp.javautil.version.MajorMinorVersion;
import org.arp.javautil.version.VersionRange;

/**
 * Wraps the {@link DatabaseMetaData} class to provide some additional methods for
 * using database metadata.
 * 
 * @author Andrew Post
 */
public class DatabaseMetaDataWrapper {

    private boolean metadataRead;
    private String databaseProductName;
    private DatabaseVersion databaseVersion;
    private final DatabaseMetaData metaData;
    private String driverName;
    private DriverVersion driverVersion;
    
    /**
     * Creates the wrapper.
     * 
     * @param metaData the {@link DatabaseMetadata} instance to wrap.
     */
    public DatabaseMetaDataWrapper(DatabaseMetaData metaData) {
        if (metaData == null) {
            throw new IllegalArgumentException("metaData cannot be null");
        }
        this.metaData = metaData;
    }

    /**
     * Compares database product name and version information to that reported 
     * by the database.
     * 
     * @param databaseProductNameRegex a regular expression that the actual
     * database product name will match. Use a regular expression that is 
     * unlikely to overlap with the product names of other database systems.
     * @param minVersion the expected minimum version, if any.
     * @param maxVersion the expected maximum version, if any.
     * @return whether the actual database product name and version match the 
     * provided arguments. The database product name comparison checks whether 
     * the actual database product name matches the provided database product 
     * name regular expression. The min and max version comparisons are 
     * inclusive.
     * 
     * @throws SQLException if an error occurs fetching metadata containing the 
     * database product name and version from the database.
     */
    public boolean isDatabaseCompatible(String databaseProductNameRegex, MajorMinorVersion minVersion, MajorMinorVersion maxVersion) throws SQLException {
        readMetaDataIfNeeded();
        if (!this.databaseProductName.matches(databaseProductNameRegex)) {
            return false;
        }

        return new VersionRange(minVersion, maxVersion).isWithinRange(this.databaseVersion);
    }

    /**
     * Compares JDBC driver name and version information to that reported 
     * by the driver.
     * 
     * @param driverName the expected driver name.
     * @param minVersion the expected minimum version, if any.
     * @param maxVersion the expected maximum version, if any.
     * @return whether the actual JDBC driver name and version match the 
     * provided arguments. The min and max version comparisons are 
     * inclusive.
     * 
     * @throws SQLException if an error occurs fetching metadata containing the
     * JDBC driver name and version from the database.
     */
    public boolean isDriverCompatible(String driverName, MajorMinorVersion minVersion, MajorMinorVersion maxVersion) throws SQLException {
        readMetaDataIfNeeded();
        if (!this.driverName.equals(driverName)) {
            return false;
        }
        
        return new VersionRange(minVersion, maxVersion).isWithinRange(this.driverVersion);
    }

    private void readMetaDataIfNeeded() throws SQLException {
        if (!this.metadataRead) {
            this.databaseProductName = this.metaData.getDatabaseProductName();
            this.databaseVersion = new DatabaseVersion(this.metaData);
            this.driverName = this.metaData.getDriverName();
            this.driverVersion = new DriverVersion(this.metaData);
        }
        this.metadataRead = true;
    }

}
