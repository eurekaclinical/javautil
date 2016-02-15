package org.arp.javautil.version;

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
 * Specifies a range of version numbers and supports checking if a specified
 * version is within that range.
 * 
 * @author Andrew Post
 * @param <E> a {@link MajorMinorVersion}.
 */
public class VersionRange<E extends MajorMinorVersion> {
    private final E minVersion;
    private final E maxVersion;

    /**
     * Creates a version range.
     * 
     * @param minVersion the lower bound.
     * @param maxVersion the upper bound.
     */
    public VersionRange(E minVersion, E maxVersion) {
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    /**
     * Returns the lower version bound.
     * 
     * @return the lower version bound.
     */
    public E getMinVersion() {
        return minVersion;
    }

    /**
     * Returns the upper version bound.
     * 
     * @return the upper version bound.
     */
    public E getMaxVersion() {
        return maxVersion;
    }
    
    /**
     * Uses the min and max version's 
     * {@link MajorMinorVersion#compare(org.arp.javautil.version.MajorMinorVersion) }
     * methods to determine whether the provided version is within range.
     * 
     * @param version the version to check.
     * 
     * @return <code>true</code> if the version to check is within range, 
     * <code>false</code> if not.
     */
    public boolean isWithinRange(E version) {
        if (this.minVersion != null && this.minVersion.compare(version) > 0) {
            return false;
        }
        if (this.maxVersion != null && this.maxVersion.compare(version) < 0) {
            return false;
        }
        return true;
    }
}
