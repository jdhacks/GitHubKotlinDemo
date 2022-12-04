package com.github.githubmvvmdemo.ui

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.github.githubmvvmdemo.MainActivity
import com.github.githubmvvmdemo.R
import com.github.githubmvvmdemo.databinding.ActivitySplashBinding
import com.github.githubmvvmdemo.utils.AppConstant

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // goToMainEntryScreen()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        goToMainEntryScreen()
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({

    }
    private fun goToMainEntryScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, AppConstant.SPLASH_SCREEN_TIME_OUT) // 3000 is the delayed time in milliseconds.
    }
}