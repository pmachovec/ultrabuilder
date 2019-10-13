import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("githooker").version("0.1.1")
    kotlin("jvm").version("1.3.41")
}

group = "com.pmachovec"
version = "0.1"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_12.toString()
}

githooker {
    hooksPath = "hooks"
    triggerTaskName = "classes"
}
