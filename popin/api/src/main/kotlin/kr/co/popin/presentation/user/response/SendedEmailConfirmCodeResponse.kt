package kr.co.popin.presentation.user.response

data class SendedEmailConfirmCodeResponse (
    val confirmCode: String?,
    val limit: Int,
    val toDaySendCount: Int
)