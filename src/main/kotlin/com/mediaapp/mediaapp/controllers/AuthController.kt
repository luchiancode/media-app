package com.mediaapp.mediaapp.controllers

import com.mediaapp.mediaapp.dtos.LoginDTO
import com.mediaapp.mediaapp.dtos.Message
import com.mediaapp.mediaapp.dtos.RegisterDTO
import com.mediaapp.mediaapp.models.User
import com.mediaapp.mediaapp.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class AuthController(private val userService: UserService) {
    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<Any> {

        val user = userService.findByEmail(body.email) ?: ResponseEntity.badRequest().body(Message("User not found"))

        return ResponseEntity.ok(user)
    }
}