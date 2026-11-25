
package com.example.shopeeaiquiz

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this).apply {
            text = "SHOPEE AI QUIZ BERHASIL DIBUKA!"
            textSize = 22f
            setPadding(32, 100, 32, 32)
        }

        setContentView(text)
    }
}
