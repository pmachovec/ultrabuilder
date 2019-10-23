# Ultrabuilder 1.0
Gradle plugin for application builds including cleaning to be triggered by one command. Useful especially when running such builds from an IDE.

### Prerequisities
* Java 12
* Gradle 5.6.2

### Usage (Kotlin DSL)
The plugin is available in the Github Maven repository `https://raw.github.com/pmachovec/mavenrepo/master`.
* In `settings.gradle.kts` load the repository with the plugin.
```
pluginManagement {
    repositories {
        ...
        maven("https://raw.github.com/pmachovec/mavenrepo/master")
        ...
    }
}
```
* In `build.gradle.kts` apply the plugin.
```
plugins {
    ...
    id("com.pmachovec.ultrabuilder").version("1.0")
    ...
}
```
* New tasks will become available for the current projects:
    - **ultraBuild** - group _build_, runs _clean_ and _build_ tasks
    - **ultraTest** - group _verification_, runs _cleanTest_ and _test_ tasks