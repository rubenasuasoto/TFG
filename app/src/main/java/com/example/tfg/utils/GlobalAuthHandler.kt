package com.example.tfg.utils

object GlobalAuthHandler {
    var onUnauthorized: (() -> Unit)? = null
}
