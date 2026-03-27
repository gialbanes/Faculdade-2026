package com.fatec.oceane_atv1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform