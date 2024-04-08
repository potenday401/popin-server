package kr.co.popin.infrastructure.config.aws.credential.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.s3")
data class AmazonS3Properties(
    val accessKey: String,
    val secretKey: String,
    val bucketName: String,
    val folderPath: String,
)
