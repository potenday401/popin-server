import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.codegen.KotlinGenerator
import org.jooq.codegen.example.JPrefixGeneratorStrategy
import org.jooq.meta.postgres.PostgresDatabase
import org.postgresql.Driver as PostgresDriver
import org.testcontainers.containers.PostgreSQLContainer

plugins {
    `jooq-gradle-plugin`()

    `flyway-gradle-plugin`()
}

dependencies {
    implementation(`uuid-generator`())

    jooqGenerator(`postgres-connector`())
}

buildscript {
    dependencies {
        classpath(`testcontainer-postgres`())
        classpath(`postgres-connector`())
        classpath(`apache-ant`())

        if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_MAC)) {
            classpath(`java-native-access`())
        }

        classpath(`flyway-postgres`())
    }
}

class PostgresTestContainer(
    dockerImageName: String
) : PostgreSQLContainer<PostgresTestContainer>(dockerImageName)

val postgresContainer = tasks.register("postgresContainer") {
    val postgres = PostgresTestContainer("postgres:16.2")
        .withDatabaseName("popin")
    postgres.start()

    extra.set("jdbcUrl", postgres.jdbcUrl)
    extra.set("username", postgres.username)
    extra.set("password", postgres.password)
    extra.set("databaseName", postgres.databaseName)
    extra.set("postgres", postgres)
}

val postgresJdbcUrl = postgresContainer.get().extra["jdbcUrl"].toString()
val postgresUsername = postgresContainer.get().extra["username"].toString()
val postgresPassword = postgresContainer.get().extra["password"].toString()
val postgresDatabaseName = postgresContainer.get().extra["databaseName"].toString()
val postgres = postgresContainer.get().extra["postgres"] as PostgresTestContainer

flyway {
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    baselineOnMigrate = true
    baselineVersion = "1.0.0"

    url = postgresJdbcUrl
    user = postgresUsername
    password = postgresPassword
}

jooq {
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = PostgresDriver::class.qualifiedName
                    url = postgresJdbcUrl
                    user = postgresUsername
                    password = postgresPassword
                }

                generator.apply {
                    name = KotlinGenerator::class.qualifiedName

                    database.apply {
                        name = PostgresDatabase::class.qualifiedName
                        inputSchema = postgresDatabaseName
                    }

                    generate.apply {
                        isDeprecated = false
                        isRecords = false
                        isImmutablePojos = false
                        isFluentSetters = false
                        isDaos = true
                    }

                    target.apply {
                        packageName = "kr.co.popin"
                        directory = "build/generated/jooq"
                    }

                    strategy.name = JPrefixGeneratorStrategy::class.qualifiedName
                }
            }
        }
    }
}

tasks {
    named<JooqGenerate>("generateJooq").configure {
        dependsOn(named("postgresContainer"))
        dependsOn(named("flywayMigrate"))

        inputs.files(fileTree("src/main/resources/db/migration"))
            .withPropertyName("migrations")
            .withPathSensitivity(PathSensitivity.RELATIVE)

        allInputsDeclared.set(true)

        doLast { postgres.stop() }
    }
}