package com.fatec.lddm_merge_skills

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform