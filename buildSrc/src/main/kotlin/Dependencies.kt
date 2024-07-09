object Java {
    fun nativeAccess(version: String = "5.14.0") = "net.java.dev.jna:jna:${version}"

    fun topologySuite(version: String = "1.19.0") = "org.locationtech.jts:jts-core:${version}"
}

object Kotlin {
    fun reflect() = "org.jetbrains.kotlin:kotlin-reflect"
    fun stdlib() = "org.jetbrains.kotlin:kotlin-stdlib"
}

object SpringBoot {
    fun webStarter() = "org.springframework.boot:spring-boot-starter-web"
    fun securityStarter() = "org.springframework.boot:spring-boot-starter-security"
    fun thymeleafStarter() = "org.springframework.boot:spring-boot-starter-thymeleaf"
    fun springdocStarter(version: String = "2.4.0") = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${version}"
    fun jooqStarter() = "org.springframework.boot:spring-boot-starter-jooq"

    fun configurationProcessor() = "org.springframework.boot:spring-boot-configuration-processor"
}

object Apache {
    fun ant(version: String = "1.10.14") = "org.apache.ant:ant:${version}"
}

object Netty {
    fun macOsNativeDns(
        version: String = "4.1.107.Final",
        classifier: String? = null
    ) = "io.netty:netty-resolver-dns-native-macos:${version}${if (classifier != null) ":${classifier}" else ""}"
}

object JJWT {
    private const val JJWT_VERSION = "0.12.5"

    fun api(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-api:${version}"
    fun impl(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-impl:${version}"
    fun jackson(version: String = JJWT_VERSION) = "io.jsonwebtoken:jjwt-jackson:${version}"
}

object Jackson {
    fun kotlinModule() = "com.fasterxml.jackson.module:jackson-module-kotlin"
}

object Postgres {
    fun connector(version: String = "42.7.2") = "org.postgresql:postgresql:${version}"
}

object Postgis {
    fun jdbc(version: String = "2023.1.0") = "net.postgis:postgis-jdbc:${version}"
}

object Jooq {
    fun metaExtensions() = "org.jooq:jooq-meta-extensions"
}

object Flyway {
    fun postgres(version: String = Versions.FLYWAY) = "org.flywaydb:flyway-database-postgresql:${version}"
}

object UUID {
    fun generator(version: String = "5.3.7") = "com.github.f4b6a3:uuid-creator:${version}"
}

object AmazonWebService {
    private const val S3_SDK_VERSION = "2.25.24"

    fun simpleEmailServiceSdk(version: String = "1.12.682") = "com.amazonaws:aws-java-sdk-ses:${version}"

    fun s3Sdk(version: String = S3_SDK_VERSION) = "software.amazon.awssdk:s3:${version}"
    fun s3TransferManagerSdk(version: String = S3_SDK_VERSION) = "software.amazon.awssdk:s3-transfer-manager:${version}"

    fun secretManagerJdbc(version: String = "2.0.2") = "com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:${version}"

    fun crtSdk(version: String = "0.29.14") = "software.amazon.awssdk.crt:aws-crt:${version}"
}

object Logger {
    fun kotlinLogging(version: String = "3.0.5") = "io.github.microutils:kotlin-logging-jvm:${version}"
}

object TestContainer {
    fun postgres(version: String = "1.19.6") = "org.testcontainers:postgresql:${version}"
}