package kr.co.popin.application.external.aws.dtos

import kr.co.popin.application.external.aws.enums.MailType

data class Mail(
    val mailType: MailType,
    val variables: Map<String, Any>? = null
)
