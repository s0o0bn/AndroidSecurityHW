package com.yoonsoobin.insecuremoviereview.util

import java.security.MessageDigest

class EncryptUtil {
    companion object {
        fun encrypt(plain: String): String =
            MessageDigest.getInstance("SHA-256")
                .digest(plain.toByteArray())
                .fold("") { str, it -> str + "%02x".format(it) }
    }
}