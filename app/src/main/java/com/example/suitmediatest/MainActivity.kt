package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userIcon: ImageView
    private lateinit var nameInput: EditText
    private lateinit var palindromeInput: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        userIcon = findViewById(R.id.userIcon)
        nameInput = findViewById(R.id.nameInput)
        palindromeInput = findViewById(R.id.palindromeInput)
        checkButton = findViewById(R.id.checkButton)
        nextButton = findViewById(R.id.nextButton)

        // Aksi untuk tombol Check
        checkButton.setOnClickListener {
            val sentence = palindromeInput.text.toString().trim()
            if (sentence.isEmpty()) {
                showAlertDialog("Silahkan masukkan kata/kalimat Palindrom")
            } else {
                if (isPalindrome(sentence)) {
                    showAlertDialog("Palindrome")
                } else {
                    showAlertDialog("Bukan Palindrome")
                }
            }
        }

        // Aksi untuk tombol Next
        nextButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            if (name.isEmpty()) {
                showAlertDialog("Silahkan masukkan nama Anda")
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(sentence: String): Boolean {
        val sanitized = sentence.replace(" ", "").lowercase()
        return sanitized == sanitized.reversed()
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
