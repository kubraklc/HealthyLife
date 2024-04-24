package com.example.healthylife.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.healthylife.R
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val text = findViewById<TextView>(R.id.signUp)
        val button = findViewById<Button>(R.id.btnLogin)

        // Login butonu için  click olayı
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Signup sayfasına gitmek için  textviewe cilck olayı
        text.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

            // Metin büyüklüğünü değiştir
            val currentTextSize = text.textSize
            val newTextSize =
                currentTextSize + 2 // Yeni büyüklüğü istediğiniz gibi ayarlayabilirsiniz
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)
        }


    }
    private fun Deneme() {

    }
}