package com.example.jwtkotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserDto(

    @NotNull
    @Size(min = 3, max = 50)
    var username: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    var password: String,

    @NotNull
    @Size(min = 3, max = 50)
    var nickname: String
) {
}