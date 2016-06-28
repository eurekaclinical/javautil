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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether {@link ConnectionSpec} returns correct database metadata for
 * the H2 database driver.
 * 
 * @author Andrew Post
 */
public class H2DatabaseMetaDataTest extends AbstractDatabaseMetaDataTest {
    
    @BeforeClass
    public static void setUpClass() throws InvalidConnectionSpecArguments {
        createConnectionSpecInstance("jdbc:h2:mem:test", null, null);
    }
    
    @AfterClass
    public static void tearDownClass() {
        destroyConnectionSpecInstance();
    }
    
    @Test
    public void testDatabaseProduct() throws SQLException {
        assertDatabaseProduct(DatabaseProduct.H2);
    }
    
    @Test
    public void testDatabaseVersion() throws SQLException {
        assertDatabaseVersion(
                new DatabaseVersion(1, 4, "1.4.185 (2015-01-16)"));
    }
    
    @Test
    public void testDriver() throws SQLException {
        assertDriver(Driver.H2);
    }
    
    @Test
    public void testDriverVersion() throws SQLException {
        assertDriverVersion(new DriverVersion(1, 4, "1.4.185 (2015-01-16)"));
    }
}
