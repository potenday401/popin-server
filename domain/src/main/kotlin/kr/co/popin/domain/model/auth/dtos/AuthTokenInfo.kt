package kr.co.popin.domain.model.auth.dtos

data class AuthTokenInfo(
    val accessToken: String,
    val refreshToken: String
)
