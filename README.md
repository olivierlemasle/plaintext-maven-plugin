plaintext-maven-plugin
======================

[![Build Status](https://travis-ci.org/olivierlemasle/plaintext-maven-plugin.svg?branch=master)](https://travis-ci.org/olivierlemasle/plaintext-maven-plugin)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.olivierlemasle.maven/plaintext-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.olivierlemasle.maven/plaintext-maven-plugin)

Goals
-----

- write-files
- help

Usage
-----

__Example 1:__ Generate a file during `generate-resources` phase (which is the default build phase)

```xml
<plugin>
  <groupId>io.github.olivierlemasle</groupId>
  <artifactId>plaintext-maven-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
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
        <goal>write-files</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

__Example 2:__ Append version number to a resource file during release

```xml
<plugin>
  <groupId>io.github.olivierlemasle</groupId>
  <artifactId>plaintext-maven-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
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
  <artifactId>maven-release-plugin</artifactId>
  <version>2.5.3</version>
  <configuration>
    <preparationGoals>clean plaintext:write-files verify</preparationGoals>
  </configuration>
</plugin>
```

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
