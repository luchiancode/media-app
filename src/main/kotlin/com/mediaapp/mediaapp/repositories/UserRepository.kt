package com.mediaapp.mediaapp.repositories

import com.mediaapp.mediaapp.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}