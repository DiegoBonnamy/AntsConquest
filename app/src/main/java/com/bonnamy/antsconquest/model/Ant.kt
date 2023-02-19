package com.bonnamy.antsconquest.model

data class Ant(
    override val health: Int,
    override val attack: Int,
    override val defense: Int,
    override val dodge: Int,
    override val speed: Int,
    val energy: Int,
    val unlocked: Boolean,
    val type: AntType,
    val number: Int
): Character(health, attack, defense, dodge, speed)

enum class AntType {
    OUVRIERE, LANCIERE, FURTIVE, CHEVAUCHEUSE, ACIER, ARDENTE, CAVERNES, SPECTRALE
}