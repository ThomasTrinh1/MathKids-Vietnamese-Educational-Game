package com.mathkids.education.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.airbnb.lottie.LottieAnimationView
import com.mathkids.education.MathKidsApplication
import com.mathkids.education.R
import com.mathkids.education.databinding.ActivitySplashBinding

/**
 * Splash Screen Activity
 * Shows app logo and loading animation while initializing core components
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var app: MathKidsApplication
    private val splashDuration = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen for Android 12+
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = MathKidsApplication.getInstance(this)
        
        setupUI()
        startSplashSequence()
    }

    private fun setupUI() {
        // Setup welcome text animation
        binding.tvWelcome.alpha = 0f
        binding.tvWelcome.animate()
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(500)
            .start()

        // Setup subtitle animation
        binding.tvSubtitle.alpha = 0f
        binding.tvSubtitle.animate()
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(1000)
            .start()

        // Setup loading animation
        binding.lottieLoading.apply {
            setAnimation(R.raw.loading_animation)
            repeatCount = LottieAnimationView.INFINITE
            playAnimation()
        }

        // Setup logo animation
        binding.ivLogo.apply {
            scaleX = 0f
            scaleY = 0f
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(800)
                .setStartDelay(200)
                .start()
        }
    }

    private fun startSplashSequence() {
        // Initialize app components in background
        initializeAppComponents()
        
        // Navigate to main activity after splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMain()
        }, splashDuration)
    }

    private fun initializeAppComponents() {
        // Pre-load audio system
        app.audioManager.preloadCommonSounds()
        
        // Pre-load database
        app.database
        
        // Pre-generate some content
        app.contentGenerator.preGenerateContent()
    }

    private fun navigateToMain() {
        // Fade out animation
        binding.root.animate()
            .alpha(0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    startMainActivity()
                }
            })
            .start()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        
        // Custom transition animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onBackPressed() {
        // Disable back button during splash
    }
}
