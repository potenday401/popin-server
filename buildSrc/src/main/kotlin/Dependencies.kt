@file:Suppress("ObjectPropertyName", "FunctionName", "SpellCheckingInspection")

val `kotlin-reflect`
    get() = "org.jetbrains.kotlin:kotlin-reflect"
val `kotlin-stdlib`
    get() = "org.jetbrains.kotlin:kotlin-stdlib"

val `spring-boot-starter-web`
    get() = "org.springframework.boot:spring-boot-starter-web"

val `spring-boot-starter-thymeleaf`
    get() = "org.springframework.boot:spring-boot-starter-thymeleaf"

fun `spring-boot-starter-springdoc-webmvc-ui`(version: String = Versions.SPRINGDOC) =
    "org.springdoc:springdoc-openapi-starter-webmvc-ui:${version}"

val `jackson-module-kotlin`
    get() = "com.fasterxml.jackson.module:jackson-module-kotlin"

val `spring-boot-configuration-processor`
    get() = "org.springframework.boot:spring-boot-configuration-processor"

val `spring-boot-starter-security`
    get() = "org.springframework.boot:spring-boot-starter-security"

val `spring-boot-starter-jooq`
    get() = "org.springframework.boot:spring-boot-starter-jooq"

fun `netty-dns-macos`(
    version: String = Versions.NETTY_DNS_MACOS,
    classifier: String? = null
) = "io.netty:netty-resolver-dns-native-macos:${version}${if (classifier != null) ":${classifier}" else ""}"

fun `uuid-generator`(
    version: String = Versions.UUID_GENERATOR
) = "com.github.f4b6a3:uuid-creator:${version}"

fun `postgres-connector`(version: String = Versions.POSTGRES) =
    "org.postgresql:postgresql:${version}"

fun `testcontainer-postgres`(version: String = Versions.TESTCONTAINER) =
    "org.testcontainers:postgresql:${version}"

fun `flyway-postgres`(version: String = Versions.FLYWAY) =
    "org.flywaydb:flyway-database-postgresql:${version}"

fun `apache-ant`(version: String = Versions.APACHE_ANT) =
    "org.apache.ant:ant:${version}"

fun `java-native-access`(version: String = Versions.JAVA_NATIVE_ACCESS) =
    "net.java.dev.jna:jna:${version}"

fun `jjwt-api`(version: String = Versions.JJWT) =
    "io.jsonwebtoken:jjwt-api:${version}"
fun `jjwt-impl`(version: String = Versions.JJWT) =
    "io.jsonwebtoken:jjwt-impl:${version}"
fun `jjwt-jackson`(version: String = Versions.JJWT) =
    "io.jsonwebtoken:jjwt-jackson:${version}"

fun `aws-sdk-ses`(version: String = Versions.AWS_SDK) =
    "com.amazonaws:aws-java-sdk-ses:${version}"