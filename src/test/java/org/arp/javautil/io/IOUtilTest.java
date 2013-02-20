package org.arp.javautil.io;

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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Andrew Post
 */
public class IOUtilTest {

    @Test
    public void testReadResourceAsLines() throws IOException {
        String[] expectedArr = {
            "foo",
            "bar",
            "baz",
            "",
            "oof",
            "rab",
            "",
            "zab",};
        List<String> expected = Arrays.asList(expectedArr);
        List<String> actual =
                IOUtil.readResourceAsLines(getClass(), "/testResource");
        Assert.assertEquals("List read incorrectly", expected, actual);
    }

    @Test
    public void testReadResourceAsLinesEmptyResource() throws IOException {
        List<String> expected = Collections.emptyList();
        List<String> actual =
                IOUtil.readResourceAsLines(getClass(), "/emptyTestResource");
        Assert.assertEquals("List read incorrectly", expected, actual);
    }
}
