package com.anything.aesencryptiondecryption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    var key: String = "mysecretkey12345" // You can use any key
    var secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val et1 = findViewById<EditText>(R.id.et1)
        val btnencrypt = findViewById<Button>(R.id.encryptBtn)
        val tvencrypt = findViewById<TextView>(R.id.encryptTV)
        val et2 = findViewById<EditText>(R.id.et2)
        val btndecrypt = findViewById<Button>(R.id.decryptBtn)
        val tvDecrypt = findViewById<TextView>(R.id.decryptTV)

        btnencrypt.setOnClickListener {
            val inputText = et1.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Input text cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                val encryptedText = encrypt(inputText)
                tvencrypt.text = encryptedText
                et2.setText(encryptedText)
            }
        }

        btndecrypt.setOnClickListener {
            val inputText = et2.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Encrypted text cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val decryptedText = decrypt(inputText)
                    tvDecrypt.text = decryptedText
                } catch (e: Exception) {
                    Toast.makeText(this, "Decryption failed. Ensure valid input.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun encrypt(string: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") // Specifying which mode of AES is to be used
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec) // Specifying the mode either encrypt or decrypt
        val encryptBytes = cipher.doFinal(string.toByteArray(Charsets.UTF_8)) // Converting the string that will be encrypted to byte array
        return Base64.encodeToString(encryptBytes, Base64.DEFAULT) // Returning the encrypted string
    }

    private fun decrypt(string: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val decryptedBytes = cipher.doFinal(Base64.decode(string, Base64.DEFAULT)) // Decoding the entered string
        return String(decryptedBytes, Charsets.UTF_8) // Returning the decrypted string
    }
}
