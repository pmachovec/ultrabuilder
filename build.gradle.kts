import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm").version("1.3.50")
    id("com.pmachovec.githooker").version("1.0.1")
    id("org.jlleitschuh.gradle.ktlint").version("9.0.0")
}

group = "com.pmachovec"
version = "1.0.1"

// REPOSITORIES AND DEPENDENCIES
repositories {
    mavenCentral()
}

dependencies {
    // implementation(gradleApi()) // Added automatically by the java-gradle-plugin
    implementation(kotlin("stdlib"))

    testImplementation("org.powermock", "powermock-api-mockito2", "2.0.2")
    testImplementation("org.powermock", "powermock-module-testng", "2.0.2")
    testImplementation("org.testng", "testng", "7.0.0")

    runtimeOnly(files(sourceSets["main"].output.resourcesDir))
}

// PUBLICATION TO MAVEN REPOSITORY
gradlePlugin {
    plugins {
        create("ultraBuilderPublication") {
            id = "com.pmachovec.ultrabuilder"
            implementationClass = "com.pmachovec.ultrabuilder.UltraBuilder"
        }
    }
}

publishing {
    repositories {
        maven {
            try {
                // 'repoUrl' variable to be set in gradle.properties
                url = uri(rootProject.extra["repoUrl"]!!)
            } catch (upe: ExtraPropertiesExtension.UnknownPropertyException) {
                println("Repository for publishing not set")
            }
        }
    }
}

// PROJECT CONFIGURATION
idea {
    module {
        outputDir = File("$buildDir/classes/kotlin/main")
        testOutputDir = File("$buildDir/classes/kotlin/test")
    }
}

githooker {
    hooksPath = "hooks"
    triggerTaskName = "taskmodels"
}

ktlint {
    disabledRules.add("import-ordering")
    verbose.set(true)
}

tasks.compileTestKotlin {
    kotlinOptions.suppressWarnings = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_12.toString()
}

tasks.withType<Test> {
    testLogging {
        displayGranularity = 4 // Prints only method names with package path
        events(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
    }

    useTestNG {
        suites("src/test/resources/testng.xml")
        useDefaultListeners = true // Generates TestNG reports instead of Gradle reports
    }
}
