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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
