package org.arp.javautil.version;

import java.util.Objects;

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

/**
 * Represents the version of an application. Has the capability of comparing
 * versions by major and minor version number.
 * 
 * @author Andrew Post
 */
public class MajorMinorVersion extends AbstractVersion {

    private final int major;
    private final int minor;
    
    /**
     * Creates a version with a major and a minor number and a 
     * <code>null</code> version string.
     * 
     * @param major the major version number.
     * @param minor the minor version number.
     */
    public MajorMinorVersion(int major, int minor) {
        this(major, minor, null);
    }
    
    /**
     * Creates a version with a major number, a minor number and a version
     * string.
     * 
     * @param major the major version number.
     * @param minor the minor version number.
     * @param versionString the version string. While the version string should
     * represent a version with the major and minor numbers specified, that is
     * not assumed in this implementation.
     */
    public MajorMinorVersion(int major, int minor, String versionString) {
        super(versionString);
        this.major = major;
        this.minor = minor;
    }

    /**
     * Returns the major version number.
     * 
     * @return the major version number.
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the minor version number.
     * 
     * @return the minor version number.
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Compares two versions by major then minor number.
     * 
     * @param otherVersion another {@link MajorMinorVersion}.
     * @return <code>-1</code> if this version is lower than 
     * <code>otherVersion</code>, <code>0</code> if they are the same version,
     * and <code>1</code> if this version is higher than 
     * <code>otherVersion</code>.
     */
    public int compare(MajorMinorVersion otherVersion) {
        int result = this.major - otherVersion.major;
        if (result == 0) {
            result = this.minor - otherVersion.minor;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + super.hashCode();
        hash = 37 * hash + this.major;
        hash = 37 * hash + this.minor;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MajorMinorVersion other = (MajorMinorVersion) obj;
        if (!super.equals(obj)) {
            return false;
        }
        if (this.major != other.major) {
            return false;
        }
        if (this.minor != other.minor) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Version " + this.major + "." + this.minor + " (" + this.getVersionString() + ")";
    }

}
