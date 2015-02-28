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

import javax.naming.Context;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Factory for creating Apache DBCP data sources. Sets the {@link Context#INITIAL_CONTEXT_FACTORY}
 * and {@link Context#URL_PKG_PREFIXES} system properties when loaded by the 
 * class loader.
 * 
 * @author Andrew Post
 */
public class BasicDataSourceFactory {
    static {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
    }
    
    /**
     * Returns a new {@link BasicDataSource}.
     * @return a new {@link BasicDataSource}.
     */
    public static BasicDataSource getInstance() {
        return new BasicDataSource();
    }
}
