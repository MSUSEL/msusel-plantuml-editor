# PlantUML Editor
A Quick and Dirty PlantUML Editor

## Introduction
This project produces 

## MSUSEL Project dependencies
This project depends on the following other MSUSEL subprojects:
1. [msusel-parent](https://github.com/MSUSEL/msusel-parent/)

## Building
There are two options:

1. You can use your own version of Maven and run the following commands at the command line, from the project root directory:
   * Compiling:
      ```bash
        $ mvn clean compile -Dmaven.test.skip=true
      ```
   * Packaging into a Jar with dependencies
      ```bash
        $ mvn clean package -Dmaven.test.skip=true
      ```
   * Packaging into a Jar and deploying to the [maven repo](https://github.com/MSUSEL/msusel-maven-repo):
      ```bash
        $ mvn clean deploy -Dmaven.test.skip=true
      ```

2. You can use the Maven wrapper which comes with the project:
   * On Mac and Linux:
      - Compiling:
      ```bash
        $ ./mvnw clean compile -Dmaven.test.skip=true
      ```
      - Packaging into a Jar with Dependencies:
      ```bash
        $ ./mvnw clean package -Dmaven.test.skip=true
      ```
      - Packaging into a Jar and deploying to the [maven repo](https://github.com/MSUSEL/msusel-maven-repo):
      ```bash
        $ ./mvnw clean deploy -Dmaven.test.skip=true
      ```
   * Windows:
      - Compiling:
      ```bash
        $ .\mvnw.cmd clean compile -Dmaven.test.skip=true
      ```
      - Packaging into a Jar with Dependencies:
      ```bash
        $ .\mvnw.cmd clean package -Dmaven.test.skip=true
      ```
      - Packaging into a Jar and deploying to the [maven repo](https://github.com/MSUSEL/msusel-maven-repo):
      ```bash
        $ .\mvnw.cmd clean deploy -Dmaven.test.skip=true
        
## License
As will all projects from MSUSEL this project is licensed under the MIT open source license. All source files associated with this project should have a copy of the license at the top of the file.

If a build fails due to license header issues, this can be remedied using the following command sequence at the command line:

- With an independently installed Maven system:
    * Linux, Mac, Windows:
    ```bash
     $ mvn license:format
    ```
- Using the Maven Wrapper:
    * Linux and Mac:
    ```bash
     $ ./mvnw license:format
    ```
    * Windows:
    ```bash
     $ .\mvnw.cmd license:format
    ```
