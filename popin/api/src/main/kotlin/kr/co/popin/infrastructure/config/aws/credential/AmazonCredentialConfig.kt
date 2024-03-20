package kr.co.popin.infrastructure.config.aws.credential

import com.amazonaws.auth.BasicAWSCredentials
import kr.co.popin.infrastructure.config.aws.credential.property.AmazonCredentialProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonCredentialConfig (
    private val amazonCredentialProperties: AmazonCredentialProperties
){
    @Bean
    fun simpleEmailServiceCredentials() = BasicAWSCredentials(
        amazonCredentialProperties.smtp.accessKey,
        amazonCredentialProperties.smtp.secretKey
    )
}