package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvSelectedUser: TextView
    private lateinit var btnSelectUser: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        tvName = findViewById(R.id.tvName)
        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        btnSelectUser = findViewById(R.id.btnSelectUser)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opsional, untuk menghapus SecondActivity dari stack
        }

        // Ambil nama dari intent dan tampilkan di TextView
        val name = intent.getStringExtra("name")
        tvName.text = name

        // Set listener untuk tombol "Pilih Pengguna"
        btnSelectUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val selectedUserName = data?.getStringExtra("selectedUserName")
            tvSelectedUser.text = selectedUserName
        }
    }
}
