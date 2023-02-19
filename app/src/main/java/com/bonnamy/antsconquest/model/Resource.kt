package com.bonnamy.antsconquest.model

data class Resource(
    val name: String,
    val type: ResourceType,
    val count: Int
)

enum class ResourceType {
    APPLE, DIRT, LEAF, MUSHROOM, ROCK, METAL
}