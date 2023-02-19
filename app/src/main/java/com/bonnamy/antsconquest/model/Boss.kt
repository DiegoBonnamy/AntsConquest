package com.bonnamy.antsconquest.model

data class Boss(
    override val health: Int,
    override val attack: Int,
    override val defense: Int,
    override val dodge: Int,
    override val speed: Int,
    val level: Int,
    val unlocked: Boolean,
    val type: BossType
) : Character(health, attack, defense, dodge, speed)

enum class BossType {
    GUEPE, ARAIGNEE, SCARABEE, MILLEPATTE
}