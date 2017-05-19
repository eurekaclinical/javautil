# Javautil
[Atlanta Clinical and Translational Science Institute (ACTSI)](http://www.actsi.org), [Emory University](http://www.emory.edu), Atlanta, GA

## What does it do?
It is a utility library to accelerate the development of Java software. It is used throughout Eureka! Clinical projects, but it is designed to be useful to other projects as well. While we use the Apache Commons libraries when possible, when we find a gap in those libraries, we fill the gap by adding to this project.

Packages and functionality:
* `arrays`: utilities to simplify working with arrays and converting between arrays and collections.
* `collections`: utilities to simplify working with collections and iterators.
* `color`: utilities to simplify working with colors, like extracting a hex code from a Java Color object.
* `datetime`: utilities to simplify working with Java's old datetime classes, like converting between Date and the SQL Date and Time.
* `io`: utilities to simplify common IO tasks, like retryable IO and temp directory creation, and proper resource cleanup.
* `junit`: utilities to simplify the creation of unit tests.
* `log`: tools for some common logging tasks, like including a timestamp in log messages, and logging elapsed time.
* `map`: an additional map implementation, and utilities for managing maps in which the values are collections.
* `password`: password generation.
* `serviceloader`: implementations of the ServiceLoader method of providing implementations of interfaces. This is now partially superceded by the JDK's own public ServiceLoader implementation.
* `sql`: convenience classes for creating database connections and managing resources properly when executing database queries.
* `stat`: a handful of statistical calculation classes.
* `string`: supports reading and writing the rows and columns of delimited files.
* `swing`: the SwingWorker class that was originally created by Sun Microsystems as an example.
* `test`: supports externalizing a list of strings for comparing collections during unit testing.
* `version`: classes for managing semantic versioning and comparing version numbers.

## Version 4.0 development series
Latest release: [![Latest release](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/javautil/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/javautil)

Additional functionality:
* Added a factory to the collections package for creating hashmaps with an expected capacity (number of key-value pairs).

## Version history
### Version 3.0
* Changed the pom to depend on Eureka! Clinical's parent pom, and changed the group id to `org.eurekaclinical`.
* Added API to get and work with database product names and versions, and JDBC driver versions.
* Moved the datastore package to its own project.

### Version 2.3
More functionality.

### Version 1.1
Initial release, with some additional functionality and performance improvement.
