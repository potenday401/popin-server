package kr.co.popin.application.external.aws

import kr.co.popin.infrastructure.config.aws.credential.property.AmazonS3Properties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3AsyncClient
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

        val uploadResult = transferManager.uploadFile(request)
            .completionFuture()
            .join()
            .response()
        uploadResult.ssekmsKeyId()

        val url = s3Client.utilities().getUrl { it.bucket(bucketName).key(key) }
        return url.toString()
    }

}