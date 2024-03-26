package kr.co.popin.presentation.user.request

data class VerifyConfirmCodeRequest(
    val email: String,
    val confirmCode: String
)
