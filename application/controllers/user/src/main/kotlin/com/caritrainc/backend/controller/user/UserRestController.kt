package com.caritrainc.backend.controller.user

import com.caritrainc.backend.database.repository.app.UserRepository
import com.caritrainc.backend.logging.Loggable
import com.caritrainc.backend.model.NewUserRequest
import com.caritrainc.backend.model.UpdateUserRequest
import com.caritrainc.backend.service.user.FlowableService
import com.caritrainc.backend.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserRestController @Autowired constructor(private val userService: UserService,
    private val userRepository: UserRepository,
    private val flowableService: FlowableService) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: LoginRequest): ResponseEntity<Any> {
        val userExists = userRepository.existsByName(credentials.username)

        return if (userExists) {
            // Fetch user details
            val userDetail = userRepository.findByName(credentials.username)!!

            // Start Flowable process and get the result
            val flowableResult = flowableService.startUserEvaluation(
                userDetail.name,
                userDetail.age,
                userDetail.membershipType,
                userDetail.status,
                userDetail.discount
            )

            // Return the response with both login success and Flowable result
            val response = mapOf(
                "message" to "OK",
                "flowableResult" to flowableResult
            )

            ResponseEntity.ok(response)
        } else {
            // User not found, return 404
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        }
    }



    data class LoginRequest(val username: String, val password: String)


    @Operation(
        summary = "Get all users",
        description = "User table information",
        parameters = [],
        responses = [
            ApiResponse(
                responseCode = "200", description = "Successfully fetched all users", content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Page::class)
                    )
                ]
            ),
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getUsers(@RequestParam(defaultValue = "10") size: Int): ResponseEntity<Any> {
        val result = userService.getAllUsers(Pageable.ofSize(size))
        return ResponseEntity.ok(result)
    }

    @Operation(
        summary = "Create a new user",
        description = "New user information is stored in the database",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created the user"),
        ]
    )
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveUser(@Valid @RequestBody user: NewUserRequest): ResponseEntity<Any> {
        val result = userService.saveUser(user.name,user.age,user.membershipType,user.status,user.discount)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @Operation(
        summary = "Update specific user",
        description = "Update user information in table",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated the user info"),
        ]
    )
    @PutMapping(
        "/{userId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUser(@PathVariable userId: UUID, @Valid @RequestBody user: UpdateUserRequest): ResponseEntity<Any> {
        userService.updateUser(userId, user.name,user.age,user.membershipType,user.status,user.discount)
        return ResponseEntity.status(HttpStatus.OK).body({})
    }

    @Operation(
        summary = "Delete the specific user",
        description = "Delete a specific user from the database",
        parameters = [],
        responses = [
            ApiResponse(responseCode = "204", description = "User successfully deleted"),
        ]
    )
    @DeleteMapping(
        "/{userId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteUser(@PathVariable userId: UUID): ResponseEntity<Any> {
        userService.deleteUser(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body({})
    }

    companion object : Loggable()
}