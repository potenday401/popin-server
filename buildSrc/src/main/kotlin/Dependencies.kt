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