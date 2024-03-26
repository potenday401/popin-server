package kr.co.popin.presentation.user.response

data class SentEmailConfirmCodeResponse (
    val limit: Int,
    val toDaySendCount: Int
)