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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrew Post
 */
public class ExpectedSetOfStringsReader {

    private static final char COMMENT_CHARACTER = '#';

    public Set<String> readAsSet(String resource, Class cls) throws IOException {
        if (resource == null) {
            throw new IllegalArgumentException("resource cannot be null");
        }
        if (cls == null) {
            cls = getClass();
        }
        Set<String> expected = new HashSet<>();
        InputStream is = cls.getResourceAsStream(resource);
        if (is == null) {
            throw new IOException("resource '" + resource + "' not found");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                int indexOfCommentChar = line.indexOf(COMMENT_CHARACTER);
                String lineMinusComment;
                if (indexOfCommentChar > -1) {
                    lineMinusComment = line.substring(0, indexOfCommentChar);
                } else {
                    lineMinusComment = line;
                }
                String lineTrimmed = lineMinusComment.trim();
                if (lineTrimmed.length() > 0) {
                    expected.add(lineTrimmed);
                }
            }
        }
        return expected;
    }
}
