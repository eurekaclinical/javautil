/*
 * #%L
 * JavaUtil
 * %%
 * Copyright (C) 2012 - 2013 Emory University
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
package org.arp.javautil.io;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * Convenience routines for reading files, resources, readers, and input
 * streams.
 *
 * @author Andrew Post
 */
public final class IOUtil {

    private static class LazyLoggerHolder {

        private static Logger instance =
                Logger.getLogger(IOUtil.class.getPackage().getName());
    }

    private IOUtil() {
    }

    /**
     * Reads everything from the given resource into a
     * <code>String</code>.
     *
     * @param classObj the <code>Class</code> from which
     * <code>Class.getResourceAsStream</code> is invoked. If <code>null</code>,
     * will use <code>IOUtil.class</code>.
     * @param resourceName a resource name <code>String</code>.
     * @return a <code>String</code>, or <code>null</code> if
     * <code>resourceName</code> is <code>null</code>.
     * @throws IOException if the resource cannot be found or if there is an
     * error reading from it.
     */
    public static String readResourceAsString(Class<?> classObj,
            String resourceName) throws IOException {
        if (classObj == null) {
            classObj = IOUtil.class;
        }
        if (resourceName == null) {
            return null;
        }
        InputStream in = classObj.getResourceAsStream(resourceName);
        return IOUtils.toString(in);
    }

    /**
     * Reads everything from a textual resource as lines.
     *
     * @param classObj the <code>Class</code> from which
     * <code>Class.getResourceAsStream</code> is invoked. If <code>null</code>,
     * will use <code>IOUtil.class</code>.
     * @param resourceName a resource name <code>String</code>.
     * @return a <code>String</code>, or <code>null</code> if
     * <code>resourceName</code> is <code>null</code>.
     * @return a {@link List<String>} containing the lines of the resource.
     * @throws IOException if the resource cannot be found or if there is an
     * error reading from it.
     */
    public static List<String> readResourceAsLines(
            Class<?> classObj, String resourceName) throws IOException {
        InputStream in = getResourceAsStream(resourceName, classObj);
        return IOUtils.readLines(in);
    }

    /**
     * Loads properties from the given resource into the given properties
     * object.
     *
     * @param properties the {@link Properties}, using the given class'
     * <code>getResourceAsStream</code> method.
     * @param classObj the {@link Class} whose <code>getResourceAsStream</code>
     * method is called, cannot be <code>null</code>.
     * @param resourceName a resource {@link String}, visible * *
     * from <code>classObj</code>'s class loader. If <code>null</code>, this
     * method does nothing.
     * @throws IOException if an error occurred reading the resource.
     */
    public static void readPropertiesFromResource(Properties properties,
            Class<?> classObj, String resourceName) throws IOException {
        if (classObj == null) {
            throw new IllegalArgumentException("classObj cannot be null");
        }
        if (resourceName != null) {
            InputStream inputStream = classObj.getResourceAsStream(resourceName);
            if (inputStream != null) {
                try {
                    properties.load(inputStream);
                } finally {
                    inputStream.close();
                }
            } else {
                throw new IOException(resourceName + " not found.");
            }
        }
    }

    /**
     * Creates a
     * <code>Properties</code> objects and loads the data in the given resource,
     * using the given class'
     * <code>getResourceAsStream</code> method.
     *
     * @param classObj the {@link Class} whose <code>getResourceAsStream</code>
     * method is called, cannot be <code>null</code>.
     * @param resourceName a resource {@link String}, visible * *
     * from <code>classObj</code>'s class loader. If <code>null</code>, this
     * methods returns an empty {@link Properties} object.
     * @return a <code>Properties</code> object.
     * @throws IOException
     */
    public static Properties createPropertiesFromResource(Class<?> classObj,
            String resourceName) throws IOException {
        Properties result = new Properties();
        readPropertiesFromResource(result, classObj, resourceName);
        return result;
    }

    public static String getUserInput(String prompt) throws IOException {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(
                System.in));
        System.out.print(prompt);
        try {
            return userIn.readLine();
        } finally {
            userIn.close();
        }
    }

    public static Properties loadPropertiesFromResource(Class<?> cls,
            String resource) throws IOException {
        return createPropertiesFromResource(cls, resource);
    }

    public static Properties[] loadPropertiesFromResources(Class<?> cls,
            String[] resources) throws IOException {
        if (resources == null) {
            return null;
        }
        Properties[] result = new Properties[resources.length];
        for (int i = 0; i < resources.length; i++) {
            result[i] = loadPropertiesFromResource(cls, resources[i]);
        }
        return result;
    }

    /**
     * Finds a resource with a given name, using this class' class loader.
     * Functions identically to
     * {@link Class#getResourceAsStream(java.lang.String)}, except it throws an
     * {@link IllegalArgumentException} if the specified resource name is
     * <code>null</code>, and it throws an {@link IOException} if no resource
     * with the specified name is found.
     *
     * @param name name {@link String} of the desired resource. Cannot be
     * <code>null</code>.
     * @return an {@link InputStream}.
     * @throws IOException if no resource with this name is found.
     */
    public static InputStream getResourceAsStream(String name)
            throws IOException {
        return getResourceAsStream(name, null);
    }

    /**
     * Finds a resource with a given name using the class loader of the
     * specified class. Functions identically to
     * {@link Class#getResourceAsStream(java.lang.String)}, except it throws an
     * {@link IllegalArgumentException} if the specified resource name is
     * <code>null</code>, and it throws an {@link IOException} if no resource
     * with the specified name is found.
     *
     * @param name name {@link String} of the desired resource. Cannot be
     * <code>null</code>.
     * @param cls the {@link Class<?>} whose loader to use.
     * @return an {@link InputStream}.
     * @throws IOException if no resource with this name is found.
     */
    public static InputStream getResourceAsStream(String name, Class<?> cls)
            throws IOException {
        if (cls == null) {
            cls = IOUtil.class;
        }
        if (name == null) {
            throw new IllegalArgumentException("resource cannot be null");
        }
        InputStream result = cls.getResourceAsStream(name);
        if (result == null) {
            throw new IOException("Could not open resource " + name
                    + " using " + cls.getName() + "'s class loader");
        }
        return result;
    }

    /**
     * Converts a resource into a temporary file that can be read by objects
     * that look up files by name. Returns the file that was created. The file
     * will be deleted when the program exits.
     *
     * @param resourceName the resource to convert. Cannot be <code>null</code>.
     * @param filePrefix the prefix of the temporary file. Cannot be
     * <code>null</code>.
     * @param fileSuffix the suffix of the temporary file.
     * @return a temporary {@link File}.
     * @throws IOException if an error occurs while writing the contents of the
     * resource to the temporary file.
     */
    public static File resourceToFile(String resourceName, String filePrefix,
            String fileSuffix) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResourceAsStream(resourceName)));
        File outFile = File.createTempFile(filePrefix, fileSuffix);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outFile)));

        int c;
        while ((c = reader.read()) != -1) {
            writer.write(c);
        }
        reader.close();
        writer.close();

        outFile.deleteOnExit();
        return outFile;
    }
    
    /**
     * Gets the logger for this package.
     *
     * @return a <code>Logger</code> object.
     */
    static Logger logger() {
        return LazyLoggerHolder.instance;
    }
}
