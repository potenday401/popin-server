package kr.co.popin.presentation.user.request

import io.swagger.v3.oas.annotations.media.Schema

data class VerifyConfirmCodeRequest(
    @Schema(description = "유저 이메일")
    val email: String,
    @Schema(description = "인증 코드")
    val confirmCode: String
)
