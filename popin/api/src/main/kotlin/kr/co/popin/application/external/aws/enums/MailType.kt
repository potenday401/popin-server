package kr.co.popin.application.external.aws.enums

enum class MailType (
    val templateName: String,
    val subject: String
) {
    MAIL_CONFIRM("mail-confirm", "이메일 인증 번호")
}