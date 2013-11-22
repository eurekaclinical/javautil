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
package org.arp.javautil.map;

import org.arp.javautil.map.ImnuMap.DefaultValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ImnuMapTest {

    private Map<Integer, String> nullMap;
    private Map<Integer, String> customMap;
    
    private class IntegerParityDefaultValue implements DefaultValue<String> {

        @Override
        public String defaultValue(Object key) {
            if (key instanceof Integer) {
                Integer k = (Integer) key;
                if (k % 2 == 0) {
                    return "EVEN";
                } else {
                    return "ODD";
                }
            } else {
                return "NAN";
            }
        }
        
    }
    
    @Before
    public void setUp() throws Exception {
        nullMap = new ImnuMap<>();
        customMap = new ImnuMap<>(new IntegerParityDefaultValue());
    }

    @After
    public void tearDown() throws Exception {
        nullMap = null;
        customMap = null;
    }
    
    @Test
    public void testGet() {
        nullMap.put(1, "ONE");
        nullMap.put(2, "TWO");
        nullMap.put(4, "FOUR");
        
        assertEquals(nullMap.get(1), "ONE");
        assertEquals(nullMap.get(2), "TWO");
        assertEquals(nullMap.get(3), null);
        assertEquals(nullMap.get(4), "FOUR");
        assertEquals(nullMap.get("foo"), null);
    }
    
    @Test
    public void testGetCustom() {
        customMap.put(1, "ONE");
        customMap.put(2, "TWO");
        customMap.put(4, "FOUR");
        
        assertEquals(customMap.get(1), "ONE");
        assertEquals(customMap.get(2), "TWO");
        assertEquals(customMap.get(3), "ODD");
        assertEquals(customMap.get(4), "FOUR");
        assertEquals(customMap.get("foo"), "NAN");
    }
}
