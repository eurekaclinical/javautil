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

import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Andrew Post
 */
public abstract class AbstractDatabaseMetaDataTest {
    private static ConnectionSpec CONNECTION_SPEC;
    
    public static void createConnectionSpecInstance(
            String url, String username, String password) 
            throws InvalidConnectionSpecArguments {
        CONNECTION_SPEC = DatabaseAPI.DRIVERMANAGER.newConnectionSpecInstance(
                        url, username, password);
    }
    
    public static void destroyConnectionSpecInstance() {
        CONNECTION_SPEC = null;
    }
    
    public static void assertDatabaseProduct(DatabaseProduct databaseProduct) 
            throws SQLException {
        assertEquals(databaseProduct, CONNECTION_SPEC.getDatabaseProduct());
    }
    
    public static void assertDatabaseVersion(DatabaseVersion databaseVersion) 
            throws SQLException {
        assertEquals(databaseVersion, CONNECTION_SPEC.getDatabaseVersion());
    }
    
    public static void assertDriver(Driver driver) throws SQLException {
        assertEquals(driver, CONNECTION_SPEC.getDriver());
    }
    
    public static void assertDriverVersion(DriverVersion driverVersion) 
            throws SQLException {
        assertEquals(driverVersion, CONNECTION_SPEC.getDriverVersion());
    }
    
}
