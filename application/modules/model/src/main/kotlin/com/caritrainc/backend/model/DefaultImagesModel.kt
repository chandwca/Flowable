package com.caritrainc.backend.model

import jakarta.validation.constraints.NotBlank

enum class ImageType {
    BUSINESS_LOGO, STAFF_PROFILE, SERVICE, ATTENDEE_IMAGE
}

data class DefaultImageRequest(
    val categoryId: String?,
    @field:NotBlank(message = "Image URL cannot be blank") val imageUrl: String,
    val type: ImageType
)