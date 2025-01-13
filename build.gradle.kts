buildscript {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath (libs.realm.gradle.plugin);
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
}