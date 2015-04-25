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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Utility class for binding {@link DataSource}s easily to the usual JNDI
 * names (<code>java:/comp/env/jdbc/&lt;DataSourceName&gt;</code>).
 * 
 * Sets the {@link Context#INITIAL_CONTEXT_FACTORY}
 * and {@link Context#URL_PKG_PREFIXES} system properties when loaded by the 
 * class loader.
 * @author Andrew Post
 */
public class DataSourceInitialContextBinder implements AutoCloseable {
    static {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
    }

    private final InitialContext initialContext;
    private final Map<DataSource, String> dataSourceBindings;

    /**
     * Creates an instance of this class, setting up an {@link InitialContext}
     * to use.
     * 
     * @throws NamingException if an error occurs setting up the 
     * {@link InitialContext}.
     */
    public DataSourceInitialContextBinder() throws NamingException {
        this.dataSourceBindings = new HashMap<>();
        this.initialContext = new InitialContext();
        this.initialContext.createSubcontext("java:");
        this.initialContext.createSubcontext("java:/comp");
        this.initialContext.createSubcontext("java:/comp/env");
        this.initialContext.createSubcontext("java:/comp/env/jdbc");
    }

    /**
     * Binds a data source to a name. The given name will be prepended with
     * <code>java:/comp/env/jdbc/</code>.
     * 
     * @param name the name to which to bind the given data source.
     * @param dataSource a {@link DataSource}.
     * @throws NamingException if an error occurs during binding.
     */
    public void bind(String name, DataSource dataSource) throws NamingException {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource cannot be null");
        }

        String jndiUrl = "java:/comp/env/jdbc/" + name;
        initialContext.bind(jndiUrl, dataSource);
        this.dataSourceBindings.put(dataSource, jndiUrl);
    }

    /**
     * Unbinds a data source.
     * 
     * @param dataSource the {@link DataSource} to unbind.
     * @throws NamingException if an error occurs during unbinding.
     */
    public void unbind(DataSource dataSource) throws NamingException {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource cannot be null");
        }
        String jndiUrl = this.dataSourceBindings.get(dataSource);
        if (jndiUrl == null) {
            throw new NamingException("No such binding");
        }
        this.initialContext.unbind(jndiUrl);
        this.dataSourceBindings.remove(dataSource);
    }
    
    /**
     * Unbinds all data sources that were previously bound by this instance of
     * this class.
     * 
     * @throws NamingException if an error occurs during unbinding.
     */
    public void unbindAll() throws NamingException {
        /**
         * Need to copy into a new collection to avoid 
         * ConcurrentModificationException (because unbind also removes the 
         * data source from dataSourceBindings).
         */
        for (DataSource dataSource : new ArrayList<>(this.dataSourceBindings.keySet())) {
            unbind(dataSource);
        }
    }

    /**
     * Unbinds all data sources currently bound by this instance of this class.
     * If successful, it then closes the {@link InitialContext}.
     * @throws Exception if an error occurs during unbinding or closing.
     */
    @Override
    public void close() throws Exception {
        unbindAll();
        initialContext.destroySubcontext("java:/comp/env/jdbc");
        initialContext.destroySubcontext("java:/comp/env");
        initialContext.destroySubcontext("java:/comp");
        initialContext.destroySubcontext("java:");
        initialContext.close();
    }

}
