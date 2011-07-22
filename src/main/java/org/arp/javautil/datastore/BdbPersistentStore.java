package org.arp.javautil.datastore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

class BdbPersistentStore<K, V> extends BdbMap<K, V> {

    private static final String CLASS_CATALOG = "java_class_catalog";
    private static Environment env;
    private static StoredClassCatalog classCatalog;

    /*
     * to keep track of the databases created in order to properly shut down the
     * environment when the system shuts down and to prevent the creation of
     * multiple databases with the same name
     */
    private static Map<String, Database> stores = new HashMap<String, Database>();

    private final String dbName;

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread("BdbPermStoreShutdownHook") {
                    @Override
                    public void run() {
                        if (env != null) {
                            for (Database db : stores.values()) {
                                db.close();
                            }
                            stores.clear();
                            classCatalog.close();
                            env.close();
                        }
                    }
                });
    }

    BdbPersistentStore(String dbName) {
        super(dbName);
        this.dbName = dbName;
    }

    @Override
    public synchronized void shutdown() {
        try {
            super.shutdown();
            stores.remove(dbName);
            if (stores.isEmpty()) {
                classCatalog.close();
                env.close();
            }
        } catch (DatabaseException ex) {
            throw new DataStoreError(ex);
        }
    }

    Database getDatabase(String dbName) {
        if (stores.containsKey(dbName)) {
            DataStoreUtil.logger().log(Level.INFO,
                    "Permanent BerkeleyDB store {0} already exists, returning it", dbName);
            return stores.get(dbName);
        }
        createEnvironmentIfNeeded();

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTemporary(false);

        Database result = env.openDatabase(null, dbName, dbConfig);
        if (result == null) {
            throw new AssertionError("Failed to create BerkeleyDB database: "
                    + dbName);
        }
        stores.put(dbName, result);

        DataStoreUtil.logger().log(Level.INFO,
                "Created BerkeleyDB permanent store with name {0}",
                new Object[] { dbName });

        return result;
    }

    ClassCatalog createOrGetClassCatalog() throws DatabaseException {
        createEnvironmentIfNeeded();
        createClassCatalogIfNeeded();

        return classCatalog;
    }

    private synchronized void createClassCatalogIfNeeded()
            throws IllegalArgumentException, DatabaseException {
        if (classCatalog == null) {
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setTemporary(false);
            dbConfig.setAllowCreate(true);
            Database catalogDb = env
                    .openDatabase(null, CLASS_CATALOG, dbConfig);
            classCatalog = new StoredClassCatalog(catalogDb);
        }
    }

    private synchronized void createEnvironmentIfNeeded()
            throws DatabaseException {
        if (env == null) {
            EnvironmentConfig envConf = new EnvironmentConfig();
            envConf.setAllowCreate(true);
            envConf.setTransactional(true);

            File envFile = new File(System.getProperty("java.io.tmpdir")
                    + System.getProperty("file.separator")
                    + System.getProperty("store.env.name"));
            if (!envFile.exists()) {
                envFile.mkdirs();
            }

            DataStoreUtil
                    .logger()
                    .log(Level.INFO,
                            "Initialized BerkeleyDB permanent store environment at {0}",
                            envFile.getAbsolutePath());
            env = new Environment(envFile, envConf);
        }
    }
}
