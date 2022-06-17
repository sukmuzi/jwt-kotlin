package com.example.jwtkotlin.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_authority")
data class Authority(

    @Id
    @Column(name = "authority_name", length = 50)
    var authorityName: String? = null
) {
}