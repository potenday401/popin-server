package kr.co.popin.application.external.aws

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import kr.co.popin.application.external.aws.dtos.Mail
import kr.co.popin.infrastructure.config.aws.credential.property.AmazonCredentialProperties
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailSender (
    private val amazonCredentialProperties: AmazonCredentialProperties,
    private val amazonSimpleEmailService: AmazonSimpleEmailService,
    private val templateEngine: TemplateEngine
) {
    @Transactional
    fun send(to: String, mail: Mail) {
        val context = Context()
            .apply {
                setVariables(mail.variables)
            }

        val template = templateEngine.process(mail.mailType.templateName, context)

        val mailRequest = SendEmailRequest()
            .withDestination(
                Destination()
                    .withToAddresses(to)
            )
            .withMessage(
                Message()
                    .withSubject(
                        Content(mail.mailType.subject)
                    )
                    .withBody(
                        Body()
                            .withHtml(
                                Content(template)
                            )
                    )
            )
            .withSource(amazonCredentialProperties.ses.fromMail)

        amazonSimpleEmailService.sendEmail(mailRequest)
    }
}