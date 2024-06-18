package kr.co.popin.domain.model.auth.enums

enum class AuthTokenType {
    @Deprecated("don't use it. no save access token")
    ACCESS,
    REFRESH
}