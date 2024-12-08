
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.6.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}