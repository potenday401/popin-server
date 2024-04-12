package kr.co.popin.application.photo

import kr.co.popin.application.external.aws.S3Uploader
import kr.co.popin.application.photo.dtos.ChangePhotoCommand
import kr.co.popin.application.photo.dtos.UploadPhotoCommand
import kr.co.popin.domain.model.photo.Photo
import kr.co.popin.infrastructure.http.enums.ErrorResponseCode
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
        return photoPersistenceAdapter.save(contentId = command.contentId,
                                            url = url,
                                            createdAt = command.createdAt)
    }

    @Transactional
    fun change(command: ChangePhotoCommand): Photo {
        val photo = photoPersistenceAdapter.getById(command.photoId)
            ?: throw NoSuchElementException(ErrorResponseCode.NOT_FOUND_RESOURCE.getRealCode())
        val oldUrl = photo.url
        val newUrl = s3Uploader.upload(command.image)

        photo.change(newUrl, command.createdAt)
        photoPersistenceAdapter.update(photo)
        s3Uploader.delete(oldUrl)
        return photo
    }

    @Transactional
    fun delete(photoId: Long) {
        // TODO: MethodArgumentResolver 추가 후 photoId -> contentId -> userId 권한 확인 필요
        val photo = photoPersistenceAdapter.getById(photoId)
            ?: throw NoSuchElementException(ErrorResponseCode.NOT_FOUND_RESOURCE.getRealCode())
        photoPersistenceAdapter.delete(photo)
        s3Uploader.delete(photo.url)
    }

}