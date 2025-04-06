package com.caritrainc.backend.controller.business

import com.caritrainc.backend.database.collection.app.Business
import com.caritrainc.backend.logging.Loggable
import com.caritrainc.backend.model.NewBusinessRequest
import com.caritrainc.backend.model.UpdateBusinessRequest
import com.caritrainc.backend.service.business.BusinessService
import com.caritrainc.backend.service.business.CoreBusinessService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/business")
class BusinessRestController @Autowired constructor(
    private val businessService: BusinessService,
    private val coreBusinessService: CoreBusinessService
) {

    @GetMapping()
    fun getBusiness(@RequestParam(defaultValue = "10") size: Int): ResponseEntity<Page<Business>> {
        val business = businessService.getAllBusinesses(Pageable.ofSize(size))
        return ResponseEntity.ok(business)
    }

    @Operation(
        summary = "Create a new Business",
        description = "New Business information is stored in the database",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created the Business information."),
        ]
    )

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createBusiness(
        request: HttpServletRequest,
        @Valid @RequestBody business: NewBusinessRequest
    ): ResponseEntity<Business> {
        coreBusinessService.createTenantBusiness(business, "")
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    fun getBusinessById(@PathVariable id: UUID): ResponseEntity<Business> {
        val business = businessService.getBusinessById(id)
        return ResponseEntity.ok(business)
    }

    @PutMapping("/{id}")
    fun updateBusiness(
        @PathVariable id: UUID,
        @Valid @RequestBody updatedBusiness: UpdateBusinessRequest
    ): ResponseEntity<Business> {
        val business = businessService.updateBusiness(id, updatedBusiness)
        return ResponseEntity.ok(business)
    }

    @DeleteMapping("/{id}")
    fun deleteBusiness(@PathVariable id: UUID): ResponseEntity<Void> {
        businessService.deleteBusiness(id)
        return ResponseEntity.noContent().build()
    }


    companion object : Loggable()
}