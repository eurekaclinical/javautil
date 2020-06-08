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
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Andrew Post
 */
public class StringUtilTest {

    @Test
    public void testNeitherEmptyNorNull() {
        assertFalse(StringUtil.getEmptyOrNull("hello world"));
    }

    @Test
    public void testEmpty() {
        assertTrue(StringUtil.getEmptyOrNull(""));
    }

    @Test
    public void testNull() {
        assertTrue(StringUtil.getEmptyOrNull(null));
    }

    @Test
    public void testEscapeAndWriteDelimitedColumnNull() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw;
            StringUtil.escapeAndWriteDelimitedColumn(null, '\t', sw);
        }
        assertEquals("NULL", thesw.toString());
    }

    //added for OMOP mapping
    @Test
    public void testEscapeAndWriteDelimitedColumnWithString() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw;
            StringUtil.escapeAndWriteDelimitedColumn("MyStr", '\t', true, sw);
        }
        assertEquals("\"MyStr\"", thesw.toString());
    }

    //added for OMOP mapping
    @Test
    public void testEscapeAndWriteDelimitedColumnWithQuotedString() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw;
            StringUtil.escapeAndWriteDelimitedColumn("My\"Str", '\t', true, sw);
        }
        assertEquals("\"My\"\"Str\"", thesw.toString());
    }

    //added for OMOP mapping
    @Test
    public void testEscapeAndWriteDelimitedColumnWithQuotedNull() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw;
            StringUtil.escapeAndWriteDelimitedColumn(null, '\t', true, sw);
        }
        assertEquals("\"NULL\"", thesw.toString());
    }

    @Test
    public void testEscapeAndWriteDelimitedColumnReplaceNull() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw; //Will be able to go away in JDK > 8.
            Map<String, String> replace = new HashMap<>();
            replace.put(null, "foo");
            StringUtil.escapeAndWriteDelimitedColumn(null, '\t', replace, sw);
        }
        assertEquals("foo", thesw.toString());
    }
    
    @Test
    public void testEscapeAndWriteQuotedDelimitedColumnReplaceNull() throws IOException {
        StringWriter thesw;
        try (StringWriter sw = new StringWriter()) {
            thesw = sw; //Will be able to go away in JDK > 8.
            Map<String, String> replace = new HashMap<>();
            replace.put(null, "foo");
            StringUtil.escapeAndWriteDelimitedColumn(null, '\t', true, replace, sw);
        }
        assertEquals("\"foo\"", thesw.toString());
    }

}
