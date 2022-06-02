package com.cookietech.admoblibrarywithmediation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cookietech.admoblibrarywithmediation.databinding.ActivityMainBinding
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNativeAdActivity.setOnClickListener{

            val intent = Intent(this,NativeAdActivity::class.java)
            startActivity(intent)

        }

    }
}