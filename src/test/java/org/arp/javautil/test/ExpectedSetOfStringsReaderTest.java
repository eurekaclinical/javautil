package org.arp.javautil.test;

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

import java.io.IOException;
import java.util.Set;
import org.arp.javautil.arrays.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andrew Post
 */
public class ExpectedSetOfStringsReaderTest {

    @Test
    public void doTestComment() throws IOException {
        Set<String> expected = Arrays.asSet(new String[]{
            "foo",
            "bar",
            "baz",
            "oof",
            "rab",
            "zab"
        });
        Set<String> actual = new ExpectedSetOfStringsReader().readAsSet("/truth/testExpectedSetOfStringsReaderTest", getClass(), '#');
        assertEquals(expected, actual);
    }
    
    @Test
    public void doTestNoComment() throws IOException {
        Set<String> expected = Arrays.asSet(new String[]{
            "foo #foobarbaz",
            "bar",
            "baz",
            "oof",
            "rab#world",
            "zab",
            "#hello"
        });
        Set<String> actual = new ExpectedSetOfStringsReader().readAsSet("/truth/testExpectedSetOfStringsReaderTest", getClass());
        assertEquals(expected, actual);
    }
    
    @Test
    public void doTestNullComment() throws IOException {
        Set<String> expected = Arrays.asSet(new String[]{
            "foo #foobarbaz",
            "bar",
            "baz",
            "oof",
            "rab#world",
            "zab",
            "#hello"
        });
        Set<String> actual = new ExpectedSetOfStringsReader().readAsSet("/truth/testExpectedSetOfStringsReaderTest", getClass(), null);
        assertEquals(expected, actual);
    }
}
