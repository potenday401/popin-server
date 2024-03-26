package kr.co.popin.infrastructure.config.aws.ses

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import kr.co.popin.infrastructure.config.aws.credential.property.AmazonCredentialProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonSimpleEmailServiceConfig (
    private val amazonCredentialProperties: AmazonCredentialProperties
) {
    @Bean
    fun amazonSimpleEmailService(
        simpleEmailServiceCredentials: BasicAWSCredentials
    ): AmazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder
        .standard()
        .withRegion(amazonCredentialProperties.ses.region)
        .withCredentials(AWSStaticCredentialsProvider(simpleEmailServiceCredentials))
        .build()
}