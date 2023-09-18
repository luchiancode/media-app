package com.mediaapp.mediaapp.controllers

import com.mediaapp.mediaapp.dtos.LoginDTO
import com.mediaapp.mediaapp.dtos.Message
import com.mediaapp.mediaapp.dtos.RegisterDTO
import com.mediaapp.mediaapp.models.User
import com.mediaapp.mediaapp.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.util.*

@RestController
@RequestMapping("api/v1")
class AuthController(private val userService: UserService) {
    val key = "0UVC4cRtdJfuVQ84xBmHeOj9cgN+VQTzymx/fhS2p44="

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {

        val user = userService.findByEmail(body.email) ?: ResponseEntity.badRequest().body(Message("User not found"))

        if (user is User) {
            if (!user.comparePassword(body.password))
                return ResponseEntity.badRequest().body(Message("Invalid password"))


            val issuer = user.id.toString()

            //val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, key).compact()

            val cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            response.addCookie(cookie)

            //return ResponseEntity.ok(jwt)
        }

        return ResponseEntity.ok(Message("success"))
    }

    @GetMapping("/user")
    fun user(@CookieValue("jwt") jwt: String): ResponseEntity<Any> {
        return try {
            val body = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).body

            ResponseEntity.ok(this.userService.getById(body.issuer.toInt()))
        } catch (e: Exception) {
            ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
    }
    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any>{
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }
}