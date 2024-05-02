package com.example.healthylife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.example.healthylife.R
import com.example.healthylife.databinding.ActivityMainBinding
import com.example.healthylife.fragment.*
import com.example.healthylife.ui.favorite.FavoriteFragment

import com.example.healthylife.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

     private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentView(HomeFragment())

        // Bottom navigationview binding erişimi
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->  fragmentView(HomeFragment())
                R.id.favorite -> fragmentView(FavoriteFragment())
                R.id.settings -> fragmentView(SettingsFragment())
                R.id.profile -> fragmentView(ProfileFragment())

                else ->{

                }
            }
            true
        }
    }

       // Fragment manager ile fragmentı başlatma
    private  fun fragmentView( fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}