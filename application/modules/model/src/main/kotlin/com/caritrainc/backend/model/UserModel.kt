package com.caritrainc.backend.model

import jakarta.validation.constraints.*

open class UpdateUserRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 5, max = 15, message = "Name must be at least 20 characters long")
    open val name: String,
    open val age:Int,
    open val membershipType: String,
    open val status: String,
    open val discount: Double
)

data class NewUserRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 5, max = 15, message = "Name must be at least 20 characters long")
    override val name: String,
    override val age: Int,
    override val membershipType: String,
    override val status: String,
    override val discount: Double
) : UpdateUserRequest(name,age,membershipType,status,discount)