import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.kotlinJvm(version: String = Versions.KOTLIN): PluginDependencySpec =
    kotlin("jvm").version(version)

fun PluginDependenciesSpec.kotlinKapt(version: String = Versions.KOTLIN): PluginDependencySpec =
    kotlin("kapt").version(version)

fun PluginDependenciesSpec.kotlinSpring(version: String = Versions.KOTLIN): PluginDependencySpec =
    kotlin("plugin.spring").version(version)

fun PluginDependenciesSpec.springBoot(version: String = "3.2.2"): PluginDependencySpec =
    id("org.springframework.boot").version(version)

fun PluginDependenciesSpec.springDependencyManagement(version: String = "1.1.4"): PluginDependencySpec =
    id("io.spring.dependency-management").version(version)

fun PluginDependenciesSpec.jooqGradle(version: String = "8.2.1"): PluginDependencySpec =
    id("nu.studer.jooq").version(version)

fun PluginDependenciesSpec.flywayGradle(version: String = Versions.FLYWAY): PluginDependencySpec =
    id("org.flywaydb.flyway").version(version)