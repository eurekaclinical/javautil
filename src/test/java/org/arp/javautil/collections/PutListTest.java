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
package org.arp.javautil.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Collections#putList}.
 * 
 * @author Andrew Post
 */
public class PutListTest {

    @Test
    public void testPutIntoEmptyMap() {
        Map<String, List<String>> testMap =
                new HashMap<>();
        Collections.putList(testMap, "foo", "bar");
        List<String> expected = new ArrayList<>();
        expected.add("bar");
        assertEquals(expected, testMap.get("foo"));
    }

    @Test
    public void testAddToListInMap() {
        Map<String, List<String>> testMap =
                new HashMap<>();
        List<String> value = new ArrayList<>();
        value.add("bar");
        testMap.put("foo", value);
        Collections.putList(testMap, "foo", "baz");
        List<String> expected = new ArrayList<>();
        expected.add("bar");
        expected.add("baz");
        assertEquals(expected, testMap.get("foo"));
    }
}
