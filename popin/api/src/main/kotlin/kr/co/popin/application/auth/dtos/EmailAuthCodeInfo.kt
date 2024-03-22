package kr.co.popin.application.auth.dtos

import kr.co.popin.domain.model.auth.aggregate.EmailAuthCode

data class EmailAuthCodeInfo (
    val authCode: String? = null,
    val limit: Int = EmailAuthCode.EMAIL_DAY_SEND_LIMIT,
    val toDaySendCount: Int
)