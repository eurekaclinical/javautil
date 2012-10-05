package org.arp.javautil.io;

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
