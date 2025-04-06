package com.caritrainc.backend.service.defaultImages

import com.caritrainc.backend.model.DefaultImageRequest
import com.caritrainc.backend.database.collection.app.DefaultImage
import com.caritrainc.backend.database.repository.app.DefaultImagesRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.exception.AppException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import com.caritrainc.backend.model.ImageType

@Service
class DefaultImagesService @Autowired constructor(
    private val defaultImagesRepository: DefaultImagesRepository
) {
    fun saveImage(request: DefaultImageRequest): DefaultImage {
        val image = DefaultImage().apply {
            this.categoryId = request.categoryId
            this.imageUrl = request.imageUrl
            this.type = request.type
        }
        defaultImagesRepository.save(image)
        return image
    }

    fun getAllImages(pageable: Pageable): Page<DefaultImage> {
        return defaultImagesRepository.findAll(pageable)
    }

    fun deleteImage(id: UUID) {
        defaultImagesRepository.deleteById(id)
    }

    fun getImageByCategoryId(categoryId: String): Optional<DefaultImage> {
        val optionalImage = defaultImagesRepository.findByCategoryId(categoryId)
        if (!optionalImage.isPresent) {
            throw AppException(exception = AppCustomException.ImageNotFoundException)
        }
        return optionalImage
    }

    fun getImageByType(type: ImageType): Optional<DefaultImage> {
        val optionalImage = defaultImagesRepository.findByType(type)
        if (!optionalImage.isPresent) {
            throw AppException(exception = AppCustomException.ImageNotFoundException)
        }
        return optionalImage
    }

    fun updateImage(id: UUID, request: DefaultImageRequest) {
        val optionalImage = defaultImagesRepository.findById(id)
        if (!optionalImage.isPresent) {
            throw AppException(exception = AppCustomException.ImageNotFoundException)
        }
        val image = optionalImage.get()
        image.categoryId = request.categoryId
        image.imageUrl = request.imageUrl
        image.type = request.type
        defaultImagesRepository.save(image)
    }

}