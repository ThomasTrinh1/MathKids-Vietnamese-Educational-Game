package com.mathkids.education.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Preference Manager for MathKids application
 * Handles app settings and user preferences
 */
class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "mathkids_preferences"
        
        // Keys
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_MUSIC_ENABLED = "music_enabled"
        private const val KEY_VIBRATE_ENABLED = "vibrate_enabled"
        private const val KEY_SPEECH_RATE = "speech_rate"
        private const val KEY_SPEECH_PITCH = "speech_pitch"
        private const val KEY_DIFFICULTY_LEVEL = "difficulty_level"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_PARENTAL_CONTROLS = "parental_controls"
        private const val KEY_MAX_PLAY_TIME = "max_play_time"
        private const val KEY_LAST_PLAY_DATE = "last_play_date"
        private const val KEY_DAILY_STREAK = "daily_streak"
        private const val KEY_TOTAL_STARS = "total_stars"
        private const val KEY_CHILD_NAME = "child_name"
        private const val KEY_CHILD_AGE = "child_age"
    }

    // First launch
    fun isFirstLaunch(): Boolean = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
    fun setFirstLaunch(isFirst: Boolean) = sharedPreferences.edit { putBoolean(KEY_FIRST_LAUNCH, isFirst) }

    // Sound settings
    fun isSoundEnabled(): Boolean = sharedPreferences.getBoolean(KEY_SOUND_ENABLED, true)
    fun setSoundEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(KEY_SOUND_ENABLED, enabled) }

    fun isMusicEnabled(): Boolean = sharedPreferences.getBoolean(KEY_MUSIC_ENABLED, true)
    fun setMusicEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(KEY_MUSIC_ENABLED, enabled) }

    fun isVibrateEnabled(): Boolean = sharedPreferences.getBoolean(KEY_VIBRATE_ENABLED, true)
    fun setVibrateEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(KEY_VIBRATE_ENABLED, enabled) }

    // Speech settings
    fun getSpeechRate(): Float = sharedPreferences.getFloat(KEY_SPEECH_RATE, 0.8f)
    fun setSpeechRate(rate: Float) = sharedPreferences.edit { putFloat(KEY_SPEECH_RATE, rate) }

    fun getSpeechPitch(): Float = sharedPreferences.getFloat(KEY_SPEECH_PITCH, 1.1f)
    fun setSpeechPitch(pitch: Float) = sharedPreferences.edit { putFloat(KEY_SPEECH_PITCH, pitch) }

    // Game settings
    fun getDifficultyLevel(): Int = sharedPreferences.getInt(KEY_DIFFICULTY_LEVEL, 1)
    fun setDifficultyLevel(level: Int) = sharedPreferences.edit { putInt(KEY_DIFFICULTY_LEVEL, level) }

    fun getLanguage(): String = sharedPreferences.getString(KEY_LANGUAGE, "vi") ?: "vi"
    fun setLanguage(language: String) = sharedPreferences.edit { putString(KEY_LANGUAGE, language) }

    // Parental controls
    fun isParentalControlsEnabled(): Boolean = sharedPreferences.getBoolean(KEY_PARENTAL_CONTROLS, false)
    fun setParentalControlsEnabled(enabled: Boolean) = sharedPreferences.edit { putBoolean(KEY_PARENTAL_CONTROLS, enabled) }

    fun getMaxPlayTimeMinutes(): Int = sharedPreferences.getInt(KEY_MAX_PLAY_TIME, 30)
    fun setMaxPlayTimeMinutes(minutes: Int) = sharedPreferences.edit { putInt(KEY_MAX_PLAY_TIME, minutes) }

    // Progress tracking
    fun getLastPlayDate(): Long = sharedPreferences.getLong(KEY_LAST_PLAY_DATE, 0)
    fun setLastPlayDate(date: Long) = sharedPreferences.edit { putLong(KEY_LAST_PLAY_DATE, date) }

    fun getDailyStreak(): Int = sharedPreferences.getInt(KEY_DAILY_STREAK, 0)
    fun setDailyStreak(streak: Int) = sharedPreferences.edit { putInt(KEY_DAILY_STREAK, streak) }

    fun getTotalStars(): Int = sharedPreferences.getInt(KEY_TOTAL_STARS, 0)
    fun setTotalStars(stars: Int) = sharedPreferences.edit { putInt(KEY_TOTAL_STARS, stars) }

    // Child profile
    fun getChildName(): String = sharedPreferences.getString(KEY_CHILD_NAME, "") ?: ""
    fun setChildName(name: String) = sharedPreferences.edit { putString(KEY_CHILD_NAME, name) }

    fun getChildAge(): Int = sharedPreferences.getInt(KEY_CHILD_AGE, 6)
    fun setChildAge(age: Int) = sharedPreferences.edit { putInt(KEY_CHILD_AGE, age) }

    // Utility methods
    fun updateDailyStreak() {
        val today = System.currentTimeMillis()
        val lastPlay = getLastPlayDate()
        val oneDayMs = 24 * 60 * 60 * 1000L
        
        if (lastPlay == 0L) {
            // First time playing
            setDailyStreak(1)
        } else {
            val daysDiff = (today - lastPlay) / oneDayMs
            when {
                daysDiff <= 1 -> {
                    // Same day or consecutive day
                    if (daysDiff == 1L) {
                        setDailyStreak(getDailyStreak() + 1)
                    }
                }
                else -> {
                    // Streak broken
                    setDailyStreak(1)
                }
            }
        }
        
        setLastPlayDate(today)
    }

    fun addStars(stars: Int) {
        setTotalStars(getTotalStars() + stars)
    }

    fun resetAllSettings() {
        sharedPreferences.edit().clear().apply()
    }

    fun exportSettings(): Map<String, Any?> {
        return sharedPreferences.all
    }

    fun importSettings(settings: Map<String, Any?>) {
        sharedPreferences.edit {
            settings.forEach { (key, value) ->
                when (value) {
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    is String -> putString(key, value)
                }
            }
        }
    }
}
