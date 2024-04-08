package kr.co.popin.application.photo

import kr.co.popin.application.content.dtos.UploadPhotoCommand
import kr.co.popin.application.external.aws.S3Uploader
import kr.co.popin.domain.model.photo.Photo
import kr.co.popin.infrastructure.persistence.photo.PhotoPersistenceAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PhotoService(
    private val photoPersistenceAdapter: PhotoPersistenceAdapter,
    private val s3Uploader: S3Uploader
) {

    @Transactional
    fun upload(command: UploadPhotoCommand): Photo {
        val url = s3Uploader.upload(command.image)
        return photoPersistenceAdapter.save(command.contentId,
                                            url,
                                            command.createdDateTime)
    }

}