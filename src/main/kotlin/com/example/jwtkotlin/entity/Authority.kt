package com.example.jwtkotlin.entity

import lombok.Builder
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_authority")
@Builder
data class Authority(

    @Id
    @Column(name = "authority_name", length = 50)
    var authorityName: String? = null
) {
}