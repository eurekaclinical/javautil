package org.arp.javautil.sql;

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
