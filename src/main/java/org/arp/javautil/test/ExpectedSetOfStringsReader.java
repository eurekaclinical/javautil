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
 * Reads a file of strings, one per line, into a {@link Set}. Optionally, it 
 * allows specifying a comment character. On lines that contain the specified
 * character, the character and the remainder of the line are ignored.
 * 
 * @author Andrew Post
 */
public final class ExpectedSetOfStringsReader {

    /**
     * Reads the file with the specifies resource name, calling 
     * {@link Class#getResourceAsStream(java.lang.String) } to access the file.
     * 
     * @param resource the name of the file resource. Cannot be 
     * <code>null</code>.
     * @param cls the class to use. If <code>null</code>, the instance of this
     * class will be used.
     * @return a {@link Set} of strings, guaranteed not <code>null</code>.
     * 
     * @throws IOException if an error occurs reading the file.
     */
    public Set<String> readAsSet(String resource, Class<?> cls) throws IOException {
        return readAsSet(resource, cls, null);
    }
    
    /**
     * Reads the file with the specifies resource name, calling 
     * {@link Class#getResourceAsStream(java.lang.String) } to access the file.
     * 
     * @param resource the name of the file resource. Cannot be 
     * <code>null</code>.
     * @param cls the class to use. If <code>null</code>, the instance of this
     * class will be used.
     * @param commentCharacter the comment character to use. Set to
     * <code>null</code> if nothing should be interpreted as a comment.
     * @return a {@link Set} of strings, guaranteed not <code>null</code>.
     * 
     * @throws IOException if an error occurs reading the file.
     */
    public Set<String> readAsSet(String resource, Class<?> cls, Character commentCharacter) throws IOException {
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
                int indexOfCommentChar;
                if (commentCharacter == null) {
                    indexOfCommentChar = -1;
                } else {
                    indexOfCommentChar = line.indexOf(commentCharacter);
                }
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
