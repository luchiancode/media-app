package com.mediaapp.mediaapp.models

import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name = ""

    @Column(unique = true)
    var email = ""

    @Column
    var password = ""
        get() = field
        set(value) {
            val paswordEncoder = BCryptPasswordEncoder()
            field = paswordEncoder.encode(value)
        }

}