package com.mediaapp.mediaapp.services

import com.mediaapp.mediaapp.models.User
import com.mediaapp.mediaapp.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun save(user: User): User{
        return this.userRepository.save(user)
    }

    fun findByEmail(email : String): User?{
        return this.userRepository.findByEmail(email)
    }
}