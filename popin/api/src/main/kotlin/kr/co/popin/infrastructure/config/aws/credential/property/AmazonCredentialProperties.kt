package kr.co.popin.infrastructure.config.aws.credential.property

import com.amazonaws.regions.Regions
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.credentials")
data class AmazonCredentialProperties (
    val smtp: AmazonCredentialsSmtpProperties
)

data class AmazonCredentialsSmtpProperties (
    val accessKey: String,
    val secretKey: String,
    val region: Regions
)