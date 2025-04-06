package com.caritrainc.backend.database.repository.app

import com.caritrainc.backend.database.collection.app.DefaultImage
import com.caritrainc.backend.model.ImageType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DefaultImagesRepository : JpaRepository<DefaultImage, UUID> {
    fun findByCategoryId(categoryId: String): Optional<DefaultImage>
    fun findByType(type: ImageType): Optional<DefaultImage>
}