package kr.co.popin.presentation.user.request

import io.swagger.v3.oas.annotations.media.Schema

data class UserLoginRequest(
    @Schema(description = "유저 이메일")
    val email: String,
    @Schema(description = "유저 비밀번호")
    val password: String
)
