package com.example.kmp_intro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform