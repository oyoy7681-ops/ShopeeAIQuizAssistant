package com.example.shopeeaiquiz

import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
        }

        val title = TextView(this).apply {
            text = "SHOPEE AI QUIZ ASSISTANT"
            textSize = 23f
        }

        val button = Button(this).apply {
            text = "PILIH SCREENSHOT KUIS"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
            }
        }

        resultText = TextView(this).apply {
            text = "Jawaban akan ditampilkan di sini."
            textSize = 17f
        }

        layout.addView(title)
        layout.addView(button)
        layout.addView(resultText)

        setContentView(layout)
    }

    @Deprecated("Legacy image picker callback")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val uri: Uri = data?.data ?: return
            readImage(uri)
        }
    }

    private fun readImage(uri: Uri) {
        resultText.text = "Sedang membaca soal..."

        try {
            val image = InputImage.fromFilePath(this, uri)
            val recognizer = TextRecognition.getClient(
                TextRecognizerOptions.DEFAULT_OPTIONS
            )

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    resultText.text = visionText.text.ifBlank {
                        "Tidak ada tulisan yang terbaca."
                    }
                }
                .addOnFailureListener {
                    resultText.text = "Gagal membaca gambar."
                }

        } catch (e: Exception) {
            resultText.text = "Gagal membuka screenshot."
        }
    }
}
