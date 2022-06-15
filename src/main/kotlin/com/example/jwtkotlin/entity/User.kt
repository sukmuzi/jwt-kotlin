package com.example.jwtkotlin.entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var userId: Long? = 0,

    @Column(name = "username", length = 50, unique = true)
    private var username: String? = null,

    @Column(name = "password", length = 100)
    private var password: String? = null,

    @Column(name = "nickname", length = 50)
    private var nickname: String? = null,

    @Column(name = "activated")
    private var activated: Boolean? = null,

    @ManyToMany
    @JoinTable(name = "user_authority")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JoinColumn(name = "authority_name", referencedColumnName = "authority_name")
    private var authorities: Set<Authority>? = null
) {
}