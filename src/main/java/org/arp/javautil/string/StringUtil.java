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
package org.arp.javautil.string;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Post
 */
public class StringUtil {

    private static final char QUOTE = '"';
    private static final char[] SEARCH_CHARS = new char[]{QUOTE, '\r', '\n'};

    private StringUtil() {
    }

    /**
     * Returns whether the given string is <code>null</code>, of length 0, or
     * contains just whitespace.
     *
     * @param str a <code>String</code>.
     * @return <code>true</code> if the string is <code>null</code>, of length
     * 0, or contains just whitespace; <code>false</code> otherwise.
     */
    public static boolean getEmptyOrNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1 != null ? str1.equals(str2) : str2 == null;
    }

    public static List<String> escapeDelimitedColumns(
            List<String> columnValues, char delimiter) {
        return escapeDelimitedColumns(columnValues, null, delimiter);
    }

    public static List<String> escapeDelimitedColumns(
            List<String> columnValues, Map<String, String> replace,
            char delimiter) {
        List<String> result = new ArrayList<>(columnValues.size());
        for (String value : columnValues) {
            value = doReplace(value, replace);
            result.add(StringUtil.escapeDelimitedColumn(value, delimiter));
        }
        return result;
    }

    public static void escapeDelimitedColumnsInPlace(List<String> columnValues,
            char delimiter) {
        escapeDelimitedColumnsInPlace(columnValues, null, delimiter);
    }

    public static void escapeDelimitedColumnsInPlace(List<String> columnValues,
            Map<String, String> replace, char delimiter) {
        for (int i = 0, n = columnValues.size(); i < n; i++) {
            String value = columnValues.get(i);
            value = doReplace(value, replace);
            columnValues.set(i, StringUtil.escapeDelimitedColumn(value,
                    delimiter));
        }
    }

    public static String[] escapeDelimitedColumns(String[] columnValues,
            char delimiter) {
        return escapeDelimitedColumns(columnValues, null, delimiter);
    }

    public static String[] escapeDelimitedColumns(String[] columnValues,
            Map<String, String> replace, char delimiter) {
        String[] result = new String[columnValues.length];
        for (int i = 0; i < columnValues.length; i++) {
            String columnValue = columnValues[i];
            columnValue = doReplace(columnValue, replace);
            result[i] = StringUtil.escapeDelimitedColumn(columnValue,
                    delimiter);
        }
        return result;
    }

    public static void escapeDelimitedColumnsInPlace(String[] columnValues,
            char delimiter) {
        escapeDelimitedColumnsInPlace(columnValues, null, delimiter);
    }

    public static void escapeDelimitedColumnsInPlace(String[] columnValues,
            Map<String, String> replace, char delimiter) {
        for (int i = 0; i < columnValues.length; i++) {
            String columnValue = columnValues[i];
            if (replace != null && replace.containsKey(columnValue)) {
                columnValue = replace.get(columnValue);
            }
            columnValues[i] = StringUtil.escapeDelimitedColumn(columnValue,
                    delimiter);
        }
    }

    public static void escapeAndWriteDelimitedColumns(String[] columnValues,
            char delimiter, Writer writer)
            throws IOException {
        escapeAndWriteDelimitedColumns(columnValues, null, delimiter, writer);
    }

    public static void escapeAndWriteDelimitedColumns(String[] columnValues,
            Map<String, String> replace,
            char delimiter, Writer writer) throws IOException {
        for (int i = 0; i < columnValues.length; i++) {
            String columnValue = columnValues[i];
            columnValue = doReplace(columnValue, replace);
            escapeAndWriteDelimitedColumn(columnValue, delimiter, writer);
            if (i < columnValues.length - 1) {
                writer.write(delimiter);
            }
        }
    }

    public static void escapeAndWriteDelimitedColumns(
            List<String> columnValues, char delimiter, Writer writer)
            throws IOException {
        escapeAndWriteDelimitedColumns(columnValues, null, delimiter, writer);
    }

    public static void escapeAndWriteDelimitedColumns(
            List<String> columnValues, Map<String, String> replace,
            char delimiter, Writer writer) throws IOException {
        for (int i = 0, n = columnValues.size(); i < n; i++) {
            String columnValue = columnValues.get(i);
            columnValue = doReplace(columnValue, replace);
            escapeAndWriteDelimitedColumn(columnValue, delimiter, writer);
            if (i < n - 1) {
                writer.write(delimiter);
            }
        }
    }

    /**
     * Escapes a column in a delimited file and writes it directly to a
     * {@link Writer}. This is somewhat more efficient than
     * {@link #escapeDelimitedColumn(java.lang.String, char)} because it does
     * less temporary object creation. The performance difference will become
     * more apparent when writing large delimited files.
     *
     * @param str a column {@link String}.
     * @param delimiter the file's delimiter character.
     * @param replace a map. Replaces any keys that are found with the 
     * corresponding values.
     * @param writer the {@link Writer} to which to write the escaped column.
     * @throws IOException if an error writing to <code>writer</code> occurs.
     */
    public static void escapeAndWriteDelimitedColumn(String str,
            char delimiter, Map<String, String> replace,
            Writer writer) throws IOException {
        escapeAndWriteDelimitedColumn(doReplace(str, replace), delimiter, writer);
    }

    /**
     * Escapes a column in a delimited file and writes it directly to a
     * {@link Writer}. This is somewhat more efficient than
     * {@link #escapeDelimitedColumn(java.lang.String, char)} because it does
     * less temporary object creation. The performance difference will become
     * more apparent when writing large delimited files.
     *
     * @param str a column {@link String}. If <code>null</code>, the string
     * <code>NULL</code> will be written out. If you want to write out something
     * different when a <code>null</code> is encountered, use
     * {@link #escapeAndWriteDelimitedColumn(java.lang.String, char, java.util.Map, java.io.Writer) }
     * and specify a replacement.
     * @param delimiter the file's delimiter character.
     * @param writer the {@link Writer} to which to write the escaped column.
     * @throws IOException if an error writing to <code>writer</code> occurs.
     */
    public static void escapeAndWriteDelimitedColumn(String str,
            char delimiter, Writer writer) throws IOException {
        if (str == null) {
            escapeAndWriteDelimitedColumn("NULL", delimiter, writer);
        } else if (containsNone(str, SEARCH_CHARS) && str.indexOf(delimiter) < 0) {
            writer.write(str);
        } else {
            writer.write(QUOTE);
            writeStr(str, writer);
            writer.write(QUOTE);
        }
    }

    /**
     * Escapes a column in a delimited file and writes a quoted string directly
     * to a {@link Writer}. This is somewhat more efficient than
     * {@link #escapeDelimitedColumn(java.lang.String, char)} because it does
     * less temporary object creation. The performance difference will become
     * more apparent when writing large delimited files.
     *
     * @param str a column {@link String}. If <code>null</code>, the string
     * <code>NULL</code> will be written out. If a quote is part of the string,
     * it will be escaped by a second quote (my"Str will be written out as
     * "my""Str"). If not, the string will be surrounded with quotes and written
     * out (myStr will be written out as "myStr").
     * @param delimiter the file's delimiter character.
     * @param addQuotes whether or not to surround the surround the column by
     * quotes.
     * @param writer the {@link Writer} to which to write the escaped column.
     * @throws IOException if an error writing to <code>writer</code> occurs.
     */
    public static void escapeAndWriteDelimitedColumn(String str,
            char delimiter, boolean addQuotes, Writer writer)
            throws IOException {
        if (str == null) {
            escapeAndWriteDelimitedColumn("NULL", delimiter, true, writer);
        } else {
            if (addQuotes) {
                writer.write(QUOTE);
                writeStr(str, writer);
                writer.write(QUOTE);
            } else {
                writeStr(str, writer);
            }
        }
    }

    /**
     * Escapes a column in a delimited file and writes a quoted string directly
     * to a {@link Writer}. This is somewhat more efficient than
     * {@link #escapeDelimitedColumn(java.lang.String, char)} because it does
     * less temporary object creation. The performance difference will become
     * more apparent when writing large delimited files.
     *
     * @param str a column {@link String}. If <code>null</code>, the string
     * <code>NULL</code> will be written out. If a quote is part of the string,
     * it will be escaped by a second quote (my"Str will be written out as
     * "my""Str"). If not, the string will be surrounded with quotes and written
     * out (myStr will be written out as "myStr").
     * @param delimiter the file's delimiter character.
     * @param addQuotes whether or not to surround the surround the column by
     * quotes.
     * @param replace a map. Replaces any keys that are found with the 
     * corresponding values.
     * @param writer the {@link Writer} to which to write the escaped column.
     * @throws IOException if an error writing to <code>writer</code> occurs.
     */
    public static void escapeAndWriteDelimitedColumn(String str,
            char delimiter, boolean addQuotes, Map<String, String> replace,
            Writer writer)
            throws IOException {
        escapeAndWriteDelimitedColumn(doReplace(str, replace), delimiter, 
                addQuotes, writer);
    }

    /**
     * Escapes a column in a delimited file.
     *
     * @param str a column {@link String}.
     * @param delimiter the file's delimiter character.
     * @return the escaped column {@link String}.
     */
    public static String escapeDelimitedColumn(String str, char delimiter) {
        if (str == null || (containsNone(str, SEARCH_CHARS) && str.indexOf(delimiter) < 0)) {
            return str;
        } else {
            StringBuilder writer = new StringBuilder();
            writer.append(QUOTE);
            for (int j = 0, n = str.length(); j < n; j++) {
                char c = str.charAt(j);
                if (c == QUOTE) {
                    writer.append(QUOTE);
                }
                writer.append(c);
            }
            writer.append(QUOTE);
            return writer.toString();
        }
    }

    private static String doReplace(String columnValue, Map<String, String> replace) {
        if (replace != null && replace.containsKey(columnValue)) {
            return replace.get(columnValue);
        } else {
            return columnValue;
        }
    }

    private static boolean containsNone(String str, char[] searchChars) {
        if (str == null || searchChars == null) {
            return true;
        }
        int csLen = str.length();
        int csLast = csLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen - 1;
        for (int i = 0; i < csLen; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchLen; j++) {
                if (searchChars[j] == ch) {
                    if (isHighSurrogate(ch)) {
                        if (j == searchLast) {
                            // missing low surrogate, fine, like String.indexOf(String)
                            return false;
                        }
                        if (i < csLast && searchChars[j + 1] == str.charAt(i + 1)) {
                            return false;
                        }
                    } else {
                        // ch is in the Basic Multilingual Plane
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isHighSurrogate(char ch) {
        return ('\uD800' <= ch && '\uDBFF' >= ch);
    }

    private static void writeStr(String str, Writer writer) throws IOException {
        for (int j = 0, n = str.length(); j < n; j++) {
            char c = str.charAt(j);
            if (c == QUOTE) {
                writer.write(QUOTE);
            }
            writer.write(c);
        }
    }
}
