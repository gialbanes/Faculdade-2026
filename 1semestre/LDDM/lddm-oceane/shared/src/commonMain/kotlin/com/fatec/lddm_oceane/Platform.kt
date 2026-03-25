package com.fatec.lddm_oceane

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform