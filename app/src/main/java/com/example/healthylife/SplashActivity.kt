package com.example.healthylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.healthylife.activity.LoginActivity
import com.example.healthylife.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    val SPLASH_DELAY: Long = 2000 // 2 saniye beklet
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val animasyon1 = AnimationUtils.loadAnimation(this,R.anim.animasyon1)
        val animasyon2 = AnimationUtils.loadAnimation(this,R.anim.animasyon2)

        val textView = binding.text1
        val textView2 = binding.text2
        val imageView= binding.imgback
        val imageView1= binding.circleImageView1
        val imageView2= binding.circleImageView2

        textView.animation = animasyon1
        textView2.animation = animasyon1
        imageView.animation = animasyon2
        imageView1.animation = animasyon1
        imageView2.animation = animasyon2


        // Handler kullanarak bir süre sonra LoginActivity'yi başlatalım
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}