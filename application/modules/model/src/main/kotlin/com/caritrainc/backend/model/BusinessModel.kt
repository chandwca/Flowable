package com.caritrainc.backend.model

import jakarta.validation.constraints.*
import java.util.*

open class UpdateBusinessRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 5, max = 15, message = "Name must be at least 20 characters long")
    open val name: String,

    @field:NotBlank(message = "About cannot be blank")
    @field:Size(min = 10, max = 200, message = "About must be at least 10 characters long")
    open val about: String,

    @field:NotBlank(message = "Email cannot be blank")
    open val email: String,

    @field:NotBlank(message = "Name cannot be blank")
    open val phoneNumber: String,

)

data class NewBusinessRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 5, max = 15, message = "Name must be at least 20 characters long")
    override val name: String,
    @field:NotBlank(message = "About cannot be blank")
    @field:Size(min = 10, max = 200, message = "About must be at least 10 characters long")
    override val about: String,

    @field:NotBlank(message = "Email cannot be blank")
    override val email: String,

    @field:NotBlank(message = "Name cannot be blank")
    override val phoneNumber: String,

) : UpdateBusinessRequest(name, about, email, phoneNumber)