package kr.co.popin.infrastructure.config.aws.s3

import kr.co.popin.infrastructure.config.aws.credential.property.AmazonS3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.SizeConstant.MB


@Configuration
class AmazonS3Config(
    private val properties: AmazonS3Properties
) {

    @Bean
    fun s3Client(): S3AsyncClient {
        val credentials = AwsBasicCredentials.create(properties.accessKey, properties.secretKey)
        return S3AsyncClient.crtBuilder()
            .credentialsProvider { credentials }
            .region(Region.AP_NORTHEAST_2)
            .minimumPartSizeInBytes(8 * MB)
            .build()
    }

    @Bean
    fun transferManager(): S3TransferManager {
        return S3TransferManager.builder()
            .s3Client(this.s3Client())
            .build()
    }

}