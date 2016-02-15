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

/**
 * Represents a database product's version.
 * 
 * @author Andrew Post
 */
public class DatabaseVersion extends MajorMinorVersion {
    
    /**
     * Creates a version with a major and a minor number and a 
     * <code>null</code> version string.
     * 
     * @param major the major version number.
     * @param minor the minor version number.
     */
    public DatabaseVersion(int major, int minor) {
        super(major, minor);
    }
    
    /**
     * Creates a version with a major number, a minor number and a version
     * string.
     * 
     * @param major the major version number.
     * @param minor the minor version number.
     * @param versionString the version string. While the version string should
     * represent a version with the major and minor numbers specified, that is
     * not assumed in this implementation.
     */
    public DatabaseVersion(int major, int minor, String versionString) {
        super(major, minor, versionString);
    }

    /**
     * Extracts the database product version from database metadata.
     * 
     * @param metaData database metadata.
     * @throws java.sql.SQLException if there was an error fetching metadata
     * from the database.
     */
    public DatabaseVersion(DatabaseMetaData metaData) throws SQLException {
        super(metaData.getDatabaseMajorVersion(), metaData.getDatabaseMinorVersion(), metaData.getDatabaseProductVersion());
    }
    
}
