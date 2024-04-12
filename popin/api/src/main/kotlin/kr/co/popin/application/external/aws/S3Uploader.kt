package kr.co.popin.application.external.aws

import kr.co.popin.infrastructure.config.aws.credential.property.AmazonS3Properties
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*


@Component
class S3Uploader(
    private val s3Properties: AmazonS3Properties,
    private val transferManager: S3TransferManager,
    private val s3Client: S3AsyncClient
) {

    fun upload(multipartFile: MultipartFile): String {
        val bucketName = s3Properties.bucketName
        val fileName = "${UUID.randomUUID()}_${multipartFile.originalFilename}"
        val buffer = ByteArrayInputStream(multipartFile.bytes)
        val file = File.createTempFile(fileName, "").apply {
            outputStream().use { buffer.copyTo(it) }
        }

        val key = "${s3Properties.folderPath}/$fileName"
        val request = UploadFileRequest.builder()
            .putObjectRequest { b -> b.bucket(bucketName).key(key) }
            .addTransferListener(LoggingTransferListener.create())
            .source(file)
            .build()

        transferManager.uploadFile(request)
            .completionFuture()
            .whenComplete { _, exception ->
                if (exception != null) {
                    throw SdkClientException.create(ErrorResponseCode.ACCESS_DENIED.getRealCode())
                }
            }.join()

        val url = s3Client.utilities().getUrl { it.bucket(bucketName).key(key) }
        return url.toString()
    }

    fun delete(url: String) {
        val prefix = s3Properties.folderPath + "/"
        val startIndex = url.indexOf(prefix) + prefix.length
        val endIndex = url.indexOf('.', startIndex)
        val key = url.substring(startIndex, endIndex + 4)

        val deleteRequest = DeleteObjectRequest.builder()
            .bucket(s3Properties.bucketName)
            .key(key)
            .build()
        s3Client.deleteObject(deleteRequest).whenComplete { _, exception ->
            if (exception != null) {
                throw SdkClientException.create(ErrorResponseCode.ACCESS_DENIED.getRealCode())
            }
        }.join()
    }

}