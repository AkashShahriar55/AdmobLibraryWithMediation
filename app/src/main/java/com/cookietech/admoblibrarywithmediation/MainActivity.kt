package com.cookietech.admoblibrarywithmediation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cookietech.admoblibrarywithmediation.databinding.ActivityMainBinding
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity
import interstitial.InterstitialAdActivity
import interstitial.InterstitialAdFragment
import rewardedad.RewardedAdActivity
import rewardedad.RewardedAdFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding

   // lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.initAdActivity.setOnClickListener {
            startInterstitialActivity()
        }
        binding.initAdFragment.setOnClickListener {
            startInterstitialFragment()
        }

        binding.rewardAdActivity.setOnClickListener {
            startRewardedAdActivity()
        }
        binding.rewardAdFragment.setOnClickListener {
            startRewardedAdFragment()
        }
    }

    fun startInterstitialActivity() {
       intent = Intent(this,InterstitialAdActivity::class.java)
        startActivity(intent)
    }
    fun startInterstitialFragment()
    {
        var transection = supportFragmentManager.beginTransaction()
        transection.add(R.id.fragmentHolder,InterstitialAdFragment())
            .addToBackStack(null)
            .commit()
    }

    fun startRewardedAdActivity(){
        intent = Intent(this,RewardedAdActivity::class.java)
        startActivity(intent)
    }
    fun startRewardedAdFragment()
    {
        var transection = supportFragmentManager.beginTransaction()
        transection.add(R.id.fragmentHolder, RewardedAdFragment())
            .addToBackStack(null)
            .commit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNativeAdActivity.setOnClickListener{

            val intent = Intent(this, NativeAdActivity::class.java)
            startActivity(intent)

        }

    }
}