package kr.co.popin.presentation.user.request

import io.swagger.v3.oas.annotations.media.Schema

data class UserDuplicateCheckRequest (
    @Schema(description = "유저 이메일")
    val email: String
)