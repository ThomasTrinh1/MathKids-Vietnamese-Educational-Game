package com.mathkids.education

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mathkids.education.audio.VietnameseAudioManager
import com.mathkids.education.database.MathKidsDatabase
import com.mathkids.education.utils.PreferenceManager
import com.mathkids.education.utils.ContentGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Main Application class for MathKids
 * Initializes core components and provides global access to services
 */
class MathKidsApplication : Application() {

    // Application scope for coroutines
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Database instance
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MathKidsDatabase::class.java,
            "mathkids_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    // Audio manager for Vietnamese TTS
    val audioManager by lazy {
        VietnameseAudioManager(applicationContext)
    }

    // Preference manager for settings
    val preferenceManager by lazy {
        PreferenceManager(applicationContext)
    }

    // Content generator for infinite exercises
    val contentGenerator by lazy {
        ContentGenerator()
    }

    companion object {
        @Volatile
        private var INSTANCE: MathKidsApplication? = null

        fun getInstance(context: Context): MathKidsApplication {
            return INSTANCE ?: synchronized(this) {
                val instance = context.applicationContext as MathKidsApplication
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        
        // Initialize core components
        initializeAudioSystem()
        initializeDatabase()
        setupDefaultPreferences()
    }

    private fun initializeAudioSystem() {
        // Initialize Vietnamese TTS
        audioManager.initialize()
    }

    private fun initializeDatabase() {
        // Database is initialized lazily when first accessed
        // This ensures it's created on a background thread
    }

    private fun setupDefaultPreferences() {
        // Set default preferences if first launch
        if (preferenceManager.isFirstLaunch()) {
            preferenceManager.apply {
                setSoundEnabled(true)
                setMusicEnabled(true)
                setVibrateEnabled(true)
                setDifficultyLevel(1)
                setLanguage("vi")
                setFirstLaunch(false)
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        // Clean up resources
        audioManager.shutdown()
    }
}
