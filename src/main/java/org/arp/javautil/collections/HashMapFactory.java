package org.arp.javautil.collections;

/*-
 * #%L
 * JavaUtil
 * %%
 * Copyright (C) 2012 - 2017 Emory University
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

/**
 *
 * @author Andrew Post
 */
public class HashMapFactory {
    public <K, V> HashMap<K, V> getInstance(int expectedSize) {
        return new HashMap<>(hashMapCapacity(expectedSize));
    }
    
    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("expectedSize cannot be < 0");
        }
        if (expectedSize < 3) {
            return expectedSize + 1;
        } else if (expectedSize < 1 << (Integer.SIZE - 2)) { //largest power of two
            return expectedSize + expectedSize / 3;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
