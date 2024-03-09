package kr.co.popin.infrastructure.config.security.enums

enum class Role (
    val code: String,
    val role: String
) {
    USER("01", "ROLE_USER"),
    ANONYMOUS("02", "ROLE_ANONYMOUS")
}