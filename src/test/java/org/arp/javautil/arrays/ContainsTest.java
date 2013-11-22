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
package org.arp.javautil.arrays;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test for the contains method.
 * 
 * @author Andrew Post
 */
public class ContainsTest {

    @Test
    public void testContainsObjectTrue() {
        assertTrue(Arrays.contains(new String[] { "hello", "world", "!" },
                "world"));
    }

    @Test
    public void testContainsObjectFalse() {
        assertFalse(Arrays.contains(new String[] { "hello", "world", "!" },
                "goodbye"));
    }

    @Test
    public void testContainsObjectNullArray() {
        assertFalse(Arrays.contains(null, "world"));
    }

    @Test
    public void testContainsObjectNullTrue() {
        assertTrue(Arrays.contains(new String[] { "hello", null, "!" }, null));
    }

    @Test
    public void testContainsObjectNullFalse() {
        assertTrue(Arrays.contains(new String[] { "hello", null, "!" }, "!"));
    }

}
