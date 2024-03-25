package kr.co.popin.presentation.user.response

data class VerifyConfirmCodeResponse(
    val limit: Int,
    val toDaySendCount: Int
)
