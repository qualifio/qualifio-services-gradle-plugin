# Qualifio Services Gradle Plugin

[![GitHub release](https://img.shields.io/github/v/release/qualifio/qualifio-services-gradle-plugin)](https://github.com/qualifio/qualifio-services-gradle-plugin/releases)
[![License](https://img.shields.io/github/license/qualifio/qualifio-services-gradle-plugin)](https://github.com/qualifio/qualifio-services-gradle-plugin/blob/main/LICENSE)

A Gradle plugin for adding the Qualifio configuration file to Android projects.

Table of contents
=================

* [Installation](#installation)
    * [Compatibility](#compatibility)
    * [Configuration](#configuration)
      * [Plugins DSL](#plugins-dsl)
      * [Legacy Plugin Application](#legacy-plugin-application)
* [Usage](#usage)

## Installation

### Compatibility

- Compatible with AGP 8.0.0 or newer ([Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin)) and Gradle 8.0 or newer.

### Configuration

#### Plugins DSL

Add the Maven Central repository to your app's `settings.gradle`

```gradle
pluginManagement {
    repositories {
        mavenCentral()
    }
}
```

Add the Qualifio Services Gradle Plugin to your project's `build.gradle`:

```gradle
plugins {
    id 'com.qualifio.gradle.qualifio-services' version '1.0.0' apply false
}
```

Apply the plugin on your app's `build.gradle`:

```gradle
plugins {
    id 'com.qualifio.gradle.qualifio-services'
}
```

#### Legacy Plugin Application

Add the Qualifio Services Gradle Plugin to your project's `build.gradle` dependencies:

```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.qualifio.gradle:qualifio-services:1.0.0'
    }
}
```

Apply the plugin on your app's `build.gradle`:

```gradle
apply plugin: 'com.qualifio.gradle.qualifio-services'
```

## Usage

To connect your app with Qualifio:
- Place the provided `qualifio-services.json` configuration file inside your app module (e.g. `app/qualifio-services.json`).
- The plugin will automatically detect the file and configure the Qualifio SDK for you.
