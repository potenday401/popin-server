object Java {
    fun jna(version: String = "5.14.0") = "net.java.dev.jna:jna:${version}"
}

object Locationtech {
    fun jtsCore(version: String = "1.19.0") = "org.locationtech.jts:jts-core:${version}"
}

object Jetbrains {
    fun kotlinReflect() = "org.jetbrains.kotlin:kotlin-reflect"
    fun kotlinStdlib() = "org.jetbrains.kotlin:kotlin-stdlib"
}

object Springframework {
    fun springBootStarterWeb() = "org.springframework.boot:spring-boot-starter-web"
    fun springBootStarterSecurity() = "org.springframework.boot:spring-boot-starter-security"
    fun springBootStarterThymeleaf() = "org.springframework.boot:spring-boot-starter-thymeleaf"
    fun springdocOpenapiStarterWebmvcUi(version: String = "2.4.0") = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${version}"
    fun springBootStarterJooq() = "org.springframework.boot:spring-boot-starter-jooq"
    fun springBootConfigurationProcessor() = "org.springframework.boot:spring-boot-configuration-processor"
}

object Apache {
    fun ant(version: String = "1.10.14") = "org.apache.ant:ant:${version}"
}

object Netty {
    fun nettyResolverDnsNativeMacos(
        version: String = "4.1.107.Final",
        classifier: String? = null
    ) = "io.netty:netty-resolver-dns-native-macos:${version}${if (classifier != null) ":${classifier}" else ""}"
}

object Jsonwebtoken {
    private const val JJWT_VERSION = "0.12.5"

    fun jjwtApi(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-api:${version}"
    fun jjwtImpl(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-impl:${version}"
    fun jjwtJackson(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-jackson:${version}"
}

object Fasterxml {
    fun jacksonModuleKotlin() = "com.fasterxml.jackson.module:jackson-module-kotlin"
}

object Postgresql {
    fun postgresql(version: String = "42.7.2") = "org.postgresql:postgresql:${version}"
}

object Postgis {
    fun postgisJdbc(version: String = "2023.1.0") = "net.postgis:postgis-jdbc:${version}"
}

object Jooq {
    fun jooqMetaExtensions() = "org.jooq:jooq-meta-extensions"
}

object Flywaydb {
    fun flywayDatabasePostgresql(version: String = Versions.FLYWAY) = "org.flywaydb:flyway-database-postgresql:${version}"
}

object F4b6a3 {
    fun uuidCreator(version: String = "5.3.7") = "com.github.f4b6a3:uuid-creator:${version}"
}

object Amazonaws {
    private const val S3_SDK_VERSION = "2.25.24"

    fun awsJavaSdkSes(version: String = "1.12.682") = "com.amazonaws:aws-java-sdk-ses:${version}"

    fun s3(version: String = S3_SDK_VERSION) = "software.amazon.awssdk:s3:${version}"
    fun s3TransferManager(version: String = S3_SDK_VERSION) = "software.amazon.awssdk:s3-transfer-manager:${version}"

    fun awsSecretsManagerJdbc(version: String = "2.0.2") = "com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:${version}"

    fun awsCrt(version: String = "0.29.14") = "software.amazon.awssdk.crt:aws-crt:${version}"
}

object Microutils {
    fun kotlinLoggingJvm(version: String = "3.0.5") = "io.github.microutils:kotlin-logging-jvm:${version}"
}

object TestContainers {
    fun postgresql(version: String = "1.19.6") = "org.testcontainers:postgresql:${version}"
}