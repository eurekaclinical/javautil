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

import java.io.File;

/**
 *
 * @author Andrew Post
 */
public class FileUtil {

    public static File replaceExtension(File file, String extension) {
        return new File(removeExtension(file) + extension);
    }

    public static String removeExtension(File file) {
        String pathname = file.getAbsolutePath();
        int indexOfTmp = pathname.lastIndexOf('.');
        return pathname.substring(0, indexOfTmp);
    }
}
