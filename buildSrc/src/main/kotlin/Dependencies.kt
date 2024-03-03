@file:Suppress("ObjectPropertyName", "FunctionName", "SpellCheckingInspection")

val `kotlin-reflect`
    get() = "org.jetbrains.kotlin:kotlin-reflect"
val `kotlin-stdlib`
    get() = "org.jetbrains.kotlin:kotlin-stdlib"

val `spring-boot-starter-web`
    get() = "org.springframework.boot:spring-boot-starter-web"

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