package com.caritrainc.backend.service.user

import com.caritrainc.backend.database.collection.app.User
import com.caritrainc.backend.database.repository.app.UserRepository
import com.caritrainc.backend.exception.AppCustomException
import com.caritrainc.backend.exception.AppException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getUser(id: UUID): Optional<User> {
        return userRepository.findById(id)
    }

    fun saveUser(name: String,age: Int,membershipType: String,status: String,discount: Double): User {
        val user = User().apply {
            this.name = name
            this.age = age
            this.membershipType = membershipType
            this.status = status
            this.discount = discount
        }
        userRepository.save(user)
        return user
    }

    fun updateUser(id: UUID, name: String, age: Int, membershipType: String, status: String, discount: Double) {
        val optionalUser = userRepository.findById(id)
        if (!optionalUser.isPresent) {
            throw AppException(exception = AppCustomException.UserNotFoundException)
        }
        val user = optionalUser.get()

        user.name = name
        user.age = age
        user.membershipType = membershipType
        user.status = status
        user.discount = discount

        userRepository.save(user)
    }


    fun deleteUser(id: UUID) {
        userRepository.deleteById(id)
    }

    fun getAllUsers(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }
}