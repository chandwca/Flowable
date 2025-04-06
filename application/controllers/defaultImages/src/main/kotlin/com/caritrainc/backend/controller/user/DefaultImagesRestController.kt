package com.caritrainc.backend.controller.defaultImages

import com.caritrainc.backend.model.DefaultImageRequest
import com.caritrainc.backend.model.ImageType
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import com.caritrainc.backend.service.defaultImages.DefaultImagesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import com.caritrainc.backend.database.collection.app.DefaultImage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
import com.caritrainc.backend.logging.Loggable

@RestController
@RequestMapping("/api/v1/defaultImages")
class DefaultImagesRestController @Autowired constructor(private val defaultImagesService: DefaultImagesService) {
    
    @Operation(
        summary = "Get all default images",
        description = "Defaultimages table information",
        parameters = [],
        responses = [
            ApiResponse(
                responseCode = "200", description = "Successfully fetched all images", content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Page::class)
                    )
                ]
            ),
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllDefaultImages(@RequestParam(defaultValue = "10") size: Int): ResponseEntity<Page<DefaultImage>> {
        val result = defaultImagesService.getAllImages(Pageable.ofSize(size))
        return ResponseEntity.ok(result)
    }

    @Operation(
        summary = "Upload default image url",
        description = "Default images with the respective types are stored in the database",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created the defaultimage"),
        ]
    )
    @PostMapping("/upload")
    fun uploadImage(@Valid @RequestBody request: DefaultImageRequest): ResponseEntity<DefaultImage> {
        val result = defaultImagesService.saveImage(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @Operation(
        summary = "Delete the default image",
        description = "Delete a specific default image from the database",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "204", description = "Default Image successfully deleted"),
        ]
    )
    @DeleteMapping(
        "/{defaultImageId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteImage(@PathVariable defaultImageId: UUID): ResponseEntity<Any> {
        defaultImagesService.deleteImage(defaultImageId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body({})
    }

    @Operation(
        summary = "Update specific default image",
        description = "Update default information in table",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated the default image info", content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Page::class)
                    )
                ]
            )        
        ]
    )
    @PutMapping(
        "/{defaultImageId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateImage(@PathVariable defaultImageId: UUID, @Valid @RequestBody image: DefaultImageRequest): ResponseEntity<Any> {
        defaultImagesService.updateImage(defaultImageId, image)
        return ResponseEntity.status(HttpStatus.OK).body({})
    }

    @Operation(
        summary = "Get images by category ID",
        description = "Fetch all default images under a specific category",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved the default images"),
        ]
    )
    @GetMapping("/category/{categoryId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getImagesByCategoryId(@PathVariable categoryId: String): ResponseEntity<Optional<DefaultImage>> {
        val result = defaultImagesService.getImageByCategoryId(categoryId)
        return ResponseEntity.ok(result)
    }

    @Operation(
        summary = "Get images by type",
        description = "Fetch all default images of a specific type",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved the default images"),
        ]
    )
    @GetMapping("/type/{type}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getImagesByType(@PathVariable type: ImageType): ResponseEntity<Optional<DefaultImage>> {
        val result = defaultImagesService.getImageByType(type)
        return ResponseEntity.ok(result)
    }
    companion object : Loggable()
}