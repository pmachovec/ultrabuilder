# Ultrabuilder 1.2
Gradle plugin for application builds including cleaning to be triggered by one command. Useful especially when running such builds from an IDE.

### Prerequisities
* Java 8 or higher
* Gradle 6.1 or higher <!-- 6.0 doesn't work -->
* Any plugin with tasks _clean_, _build_, _cleanTest_ and _test_ (e.g., the _java_ plugin)

### Usage
* The plugin is available in the Github Maven repository `https://raw.github.com/pmachovec/mavenrepo/master`.
* In Gradle settings script load the repository with the plugin. The Gradle plugin portal is also needed.
* In Gradle build script apply the plugin. Another plugin with tasks _clean_, _build_, _cleanTest_ and _test_ is required.
* By applying the plugin new tasks will become available for the actual project:
  - **ultraBuild** - group _build_, runs _clean_ and _build_ tasks
  - **ultraTest** - group _verification_, runs _cleanTest_ and _test_ tasks

#### Kotlin DSL
* _settings.gradle.kts_:
```
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://raw.github.com/pmachovec/mavenrepo/master")
    }
}
```
* _build.gradle.kts_:
```
plugins {
    PLUGIN WITH REQUIRED TASKS
    id("com.pmachovec.ultrabuilder") version "1.2" 
}
```

#### Groovy DSL
* _settings.gradle_:
```
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url 'https://raw.github.com/pmachovec/mavenrepo/master'
        }
    }
}
```
* _build.gradle_:
```
plugins {
    id 'PLUGIN WITH REQUIRED TASKS'
    id 'com.pmachovec.ultrabuilder' version '1.2'
}
```

### Release track
**1.2**
* made compatible with Java 8

<br/>

**1.1.2**
* imports in the code organized

<br/>

**1.1.1**
* username omitted for package repository publication configuration

<br/>

**1.1**
* fixed not detecting Java plugin tasks
* ready for publishing to a package repository
