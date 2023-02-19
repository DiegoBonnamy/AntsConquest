package com.bonnamy.antsconquest.model

abstract class Character(
    open val health: Int,
    open val attack: Int,
    open val defense: Int,
    open val dodge: Int,
    open val speed: Int
)