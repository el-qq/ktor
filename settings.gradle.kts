/*
 * Copyright 2014-2020 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlinx-atomicfu") {
                useModule("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "ktor"

val native_targets_enabled = !extra.has("disable_native_targets")
val CACHE_USER = System.getenv("GRADLE_CACHE_USER")

if (CACHE_USER != null) {
    val CACHE_PASSWORD = System.getenv("GRADLE_CACHE_PASSWORD")
    buildCache {
        remote(HttpBuildCache::class) {
            isPush = true
            setUrl("https://ktor-gradle-cache.teamcity.com/cache/")
            credentials {
                username = CACHE_USER
                password = CACHE_PASSWORD
            }
        }
    }
}

val fullVersion = System.getProperty("java.version", "8.0.0")
val versionComponents = fullVersion
    .split(".")
    .take(2)
    .filter { it.isNotBlank() }
    .map { Integer.parseInt(it) }

val currentJdk = if (versionComponents[0] == 1) versionComponents[1] else versionComponents[0]


include(":ktor-http")
include(":ktor-http:ktor-http-cio")
include(":ktor-io")
include(":ktor-utils")
include(":ktor-network")
include(":ktor-network:ktor-network-tls")
include(":ktor-network:ktor-network-tls:ktor-network-tls-certificates")
include(":ktor-bom")
include(":ktor-test-dispatcher")
include(":ktor-shared")
include(":ktor-shared:ktor-resources")
include(":ktor-shared:ktor-serialization")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx:ktor-serialization-kotlinx-tests")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx:ktor-serialization-kotlinx-json")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx:ktor-serialization-kotlinx-cbor")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx:ktor-serialization-kotlinx-xml")
include(":ktor-shared:ktor-serialization:ktor-serialization-kotlinx:ktor-serialization-kotlinx-protobuf")
include(":ktor-shared:ktor-events")
include(":ktor-shared:ktor-websocket-serialization")
include(":ktor-shared:ktor-websockets")
include(":ktor-java-modules-test")
