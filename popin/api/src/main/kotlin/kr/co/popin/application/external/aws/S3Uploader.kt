package kr.co.popin.application.external.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener
import java.io.File
import java.util.*


@Component
class S3Uploader(
    @Value("aws.s3.bucket-name")
    private val bucketName: String,
    private val transferManager: S3TransferManager,
    private val s3Client: S3AsyncClient
) {

    fun upload(multipartFile: MultipartFile): String {
        val fileName = "${UUID.randomUUID()}_${multipartFile.originalFilename}"
        val file = File(fileName)
        multipartFile.transferTo(file)

        val request = UploadFileRequest.builder()
            .putObjectRequest { b -> b.bucket(bucketName).key(fileName) }
            .addTransferListener(LoggingTransferListener.create())
            .source(file)
            .build()

        val uploadResult = transferManager.uploadFile(request)
            .completionFuture()
            .join()
            .response()
        uploadResult.ssekmsKeyId()

        val url = s3Client.utilities().getUrl { it.bucket(bucketName).key(fileName) }
        return url.toString()
    }

}