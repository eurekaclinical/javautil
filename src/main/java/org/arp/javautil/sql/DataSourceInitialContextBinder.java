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
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Andrew Post
 */
public class DataSourceInitialContextBinder {

    private final Map<DataSource, Binding> initialContexts;

    public DataSourceInitialContextBinder() {
        this.initialContexts = new HashMap<>();
    }

    public void bind(String name, DataSource dataSource) throws NamingException {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource cannot be null");
        }
        InitialContext initialContext = new InitialContext();
        initialContext.createSubcontext("java:");
        initialContext.createSubcontext("java:/comp");
        initialContext.createSubcontext("java:/comp/env");
        initialContext.createSubcontext("java:/comp/env/jdbc");
        String jndiUrl = "java:/comp/env/jdbc/" + name;
        initialContext.bind(jndiUrl, dataSource);
        this.initialContexts.put(dataSource, new Binding(jndiUrl, initialContext));
    }

    public void unbind(DataSource dataSource) throws NamingException {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource cannot be null");
        }
        Binding binding = this.initialContexts.get(dataSource);
        if (binding == null) {
            throw new NamingException("No such binding");
        }
        binding.unbindAndClose();
    }

    private static class Binding {

        private final String jndiUrl;
        private final InitialContext initialContext;

        public Binding(String jndiUrl, InitialContext initialContext) {
            this.jndiUrl = jndiUrl;
            this.initialContext = initialContext;
        }

        public String getJndiUrl() {
            return jndiUrl;
        }

        public InitialContext getInitialContext() {
            return initialContext;
        }

        public void unbindAndClose() throws NamingException {
            initialContext.unbind(this.jndiUrl);
            initialContext.destroySubcontext("java:/comp/env/jdbc");
            initialContext.destroySubcontext("java:/comp/env");
            initialContext.destroySubcontext("java:/comp");
            initialContext.destroySubcontext("java:");
            initialContext.close();
        }
    }

}
