package com.example.jwtkotlin.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "authority")
data class Authority(

    @Id
    @Column(name = "authority_name", length = 50)
    private var authorityName: String? = null
) {
}