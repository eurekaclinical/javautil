package org.arp.javautil.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

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
/**
 *
 * @author Andrew Post
 */
public abstract class AbstractConnectionSpec implements ConnectionSpec {

    private final boolean autoCommitEnabled;
    private DatabaseProduct databaseProduct;
    private DatabaseVersion databaseVersion;
    private Driver driverName;
    private DriverVersion driverVersion;
    
    AbstractConnectionSpec(boolean autoCommitEnabled) {
        this.autoCommitEnabled = autoCommitEnabled;
    }

    @Override
    public boolean isAutoCommitEnabled() {
        return this.autoCommitEnabled;
    }

    @Override
    public DatabaseProduct getDatabaseProduct() throws SQLException {
        readMetaDataIfNeeded();
        return this.databaseProduct;
    }

    @Override
    public DatabaseVersion getDatabaseVersion() throws SQLException {
        readMetaDataIfNeeded();
        return this.databaseVersion;
    }

    @Override
    public Driver getDriver() throws SQLException {
        readMetaDataIfNeeded();
        return this.driverName;
    }

    @Override
    public DriverVersion getDriverVersion() throws SQLException {
        readMetaDataIfNeeded();
        return this.driverVersion;
    }

    private void readMetaDataIfNeeded() throws SQLException {
        if (this.databaseVersion == null) {
            try (Connection cn = getOrCreate()) {
                DatabaseMetaData metaData = cn.getMetaData();
                this.databaseProduct = DatabaseProduct.fromMetaData(metaData);
                this.databaseVersion = new DatabaseVersion(metaData);
                this.driverName = Driver.fromMetaData(metaData);
                this.driverVersion = new DriverVersion(metaData);
            }
        }
    }

}
