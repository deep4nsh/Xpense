buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath (libs.realm.gradle.plugin);
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
}