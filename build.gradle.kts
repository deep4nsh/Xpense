buildscript {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath (libs.realm.gradle.plugin);
        classpath ("com.google.gms:google-services:4.3.10")

    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}