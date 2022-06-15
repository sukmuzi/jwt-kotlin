package com.example.jwtkotlin.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class LoginDto(

    @NotNull
    @Size(min = 3, max = 50)
    private var username: String,

    @NotNull
    @Size(min = 3, max = 100)
    private var password: String
) {
}