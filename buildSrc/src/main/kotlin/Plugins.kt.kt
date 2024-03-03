@file:Suppress("ObjectPropertyName", "FunctionName", "SpellCheckingInspection")

import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`kotlin-jvm`: PluginDependencySpec
    get() = kotlin("jvm")
fun PluginDependenciesSpec.`kotlin-jvm`(version: String = Versions.KOTLIN): PluginDependencySpec =
    `kotlin-jvm`.version(version)

val PluginDependenciesSpec.`kotlin-kapt`: PluginDependencySpec
    get() = kotlin("kapt")
fun PluginDependenciesSpec.`kotlin-kapt`(version: String = Versions.KOTLIN): PluginDependencySpec =
    `kotlin-kapt`.version(version)

val PluginDependenciesSpec.`kotlin-spring`: PluginDependencySpec
    get() = kotlin("plugin.spring")
fun PluginDependenciesSpec.`kotlin-spring`(version: String = Versions.KOTLIN): PluginDependencySpec =
    `kotlin-spring`.version(version)

val PluginDependenciesSpec.`spring-boot`: PluginDependencySpec
    get() = id("org.springframework.boot")
fun PluginDependenciesSpec.`spring-boot`(version: String = Versions.SRPING_BOOT): PluginDependencySpec =
    `spring-boot`.version(version)

val PluginDependenciesSpec.`spring-dependency-management`: PluginDependencySpec
    get() = id("io.spring.dependency-management")
fun PluginDependenciesSpec.`spring-dependency-management`(version: String = Versions.SPRING_DEPENDENCY_MANAGEMENT): PluginDependencySpec =
    `spring-dependency-management`.version(version)

val PluginDependenciesSpec.`jooq-gradle-plugin`: PluginDependencySpec
    get() = id("nu.studer.jooq")
fun PluginDependenciesSpec.`jooq-gradle-plugin`(version: String = Versions.JOOQ_GRADLE_PLUGIN): PluginDependencySpec =
    `jooq-gradle-plugin`.version(version)

val PluginDependenciesSpec.`flyway-gradle-plugin`: PluginDependencySpec
    get() = id("org.flywaydb.flyway")
fun PluginDependenciesSpec.`flyway-gradle-plugin`(version: String = Versions.FLYWAY): PluginDependencySpec =
    `flyway-gradle-plugin`.version(version)