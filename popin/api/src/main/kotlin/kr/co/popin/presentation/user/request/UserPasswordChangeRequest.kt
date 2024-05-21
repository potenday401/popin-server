package kr.co.popin.presentation.user.request

import io.swagger.v3.oas.annotations.media.Schema

data class UserPasswordChangeRequest (
    @Schema(description = "현재 유저 비밀번호")
    val currentPassword: String,
    @Schema(description = "변경할 비밀번호")
    val changePassword: String
)