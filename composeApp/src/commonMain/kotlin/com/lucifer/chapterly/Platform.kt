package com.lucifer.chapterly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform