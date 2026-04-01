package com.fatec.ocene

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform