plaintext-maven-plugin
======================

[![Build Status](https://travis-ci.org/olivierlemasle/plaintext-maven-plugin.svg?branch=master)](https://travis-ci.org/olivierlemasle/plaintext-maven-plugin)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.olivierlemasle.maven/plaintext-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.olivierlemasle.maven/plaintext-maven-plugin)

Goals
-----

- `plaintext:write`
- `plaintext:help`

Usage
-----

### Example 1: Generate a file during `generate-resources` phase

```xml
<plugins>
  <plugin>
    <groupId>io.github.olivierlemasle.maven</groupId>
    <artifactId>plaintext-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
      <files>
        <file>
          <name>main/resources/META-INF/file-name.txt</name>
          <lines>
            <line>This is a test file.</line>
            <line>Packaged by ${build_user}</line>
          </lines>
        </file>
      </files>
    </configuration>
    <executions>
      <execution>
        <id>generate-file</id>
        <goals>
          <goal>write</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
</plugins>
```

This will create during `generate-resources` phase a file `file-name.txt` in
`target/main/resources/META-INF`, with the content defined in the POM file, using property
extrapolation if needed. If the file already exists, it will be overridden.

### Example 2: Append version number to a resource file during release

```xml
<plugins>
  <plugin>
    <groupId>io.github.olivierlemasle.maven</groupId>
    <artifactId>plaintext-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
      <outputDirectory>${project.basedir}/src/main/resources</outputDirectory>
      <files>
        <file>
          <name>versions</name>
          <append>true</append>
          <lines>
            <line>${project.version}</line>
          </lines>
        </file>
      </files>
    </configuration>
  </plugin>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-scm-plugin</artifactId>
    <version>1.9.5</version>
    <configuration>
      <includes>src/main/resources/versions</includes>
      <message>Append ${project.version} to "versions"</message>
    </configuration>
  </plugin>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-release-plugin</artifactId>
    <version>2.5.3</version>
    <configuration>
      <preparationGoals>clean plaintext:write scm:add scm:checkin verify</preparationGoals>
    </configuration>
  </plugin>
</plugins>
```

With this configuration, `mvn release:prepare` will append the project version to a file
named `versions` in source directory `src/main/resources`, or create it if the file does not exist.
It will then add the file to version control and commit it while preparing the release.


License
-------

```
Copyright 2016 Olivier Lemasle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
