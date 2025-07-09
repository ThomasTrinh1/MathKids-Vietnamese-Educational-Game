package com.mathkids.education.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathkids.education.MathKidsApplication
import com.mathkids.education.database.entities.ProgressEntity
import com.mathkids.education.database.entities.UserStatsEntity
import kotlinx.coroutines.launch

/**
 * ViewModel for MainActivity
 * Manages user progress, statistics, and module unlock status
 */
class MainViewModel : ViewModel() {

    private val _totalProgress = MutableLiveData<Int>()
    val totalProgress: LiveData<Int> = _totalProgress

    private val _dailyStreak = MutableLiveData<Int>()
    val dailyStreak: LiveData<Int> = _dailyStreak

    private val _totalStars = MutableLiveData<Int>()
    val totalStars: LiveData<Int> = _totalStars

    private val _userStats = MutableLiveData<UserStatsEntity>()
    val userStats: LiveData<UserStatsEntity> = _userStats

    private val _moduleProgress = MutableLiveData<Map<String, ProgressEntity>>()
    val moduleProgress: LiveData<Map<String, ProgressEntity>> = _moduleProgress

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                // Load user statistics
                loadUserStats()
                
                // Load module progress
                loadModuleProgress()
                
                // Calculate total progress
                calculateTotalProgress()
                
            } catch (e: Exception) {
                e.printStackTrace()
                // Set default values on error
                _totalProgress.value = 0
                _dailyStreak.value = 0
                _totalStars.value = 0
            }
        }
    }

    private suspend fun loadUserStats() {
        // TODO: Load from database
        // For now, use default values
        val defaultStats = UserStatsEntity(
            totalStars = 0,
            totalExercises = 0,
            correctAnswers = 0,
            totalPlayTime = 0,
            dailyStreak = 0,
            longestStreak = 0,
            averageAccuracy = 0f,
            lastPlayDate = System.currentTimeMillis()
        )
        
        _userStats.value = defaultStats
        _totalStars.value = defaultStats.totalStars
        _dailyStreak.value = defaultStats.dailyStreak
    }

    private suspend fun loadModuleProgress() {
        // TODO: Load from database
        // For now, create empty progress map
        val progressMap = mutableMapOf<String, ProgressEntity>()
        
        // Default modules
        val modules = listOf("counting", "addition", "subtraction", "number_recognition")
        modules.forEach { moduleId ->
            progressMap[moduleId] = ProgressEntity(
                moduleId = moduleId,
                level = 1,
                starsEarned = 0,
                totalExercises = 10,
                completedExercises = 0,
                averageTime = 0,
                accuracy = 0f,
                isCompleted = false
            )
        }
        
        _moduleProgress.value = progressMap
    }

    private fun calculateTotalProgress() {
        val progressMap = _moduleProgress.value ?: return
        
        var totalCompleted = 0
        var totalExercises = 0
        
        progressMap.values.forEach { progress ->
            totalCompleted += progress.completedExercises
            totalExercises += progress.totalExercises
        }
        
        val progressPercentage = if (totalExercises > 0) {
            (totalCompleted * 100) / totalExercises
        } else {
            0
        }
        
        _totalProgress.value = progressPercentage
    }

    /**
     * Check if a module is unlocked
     */
    fun isModuleUnlocked(moduleId: String): Boolean {
        return when (moduleId) {
            "counting" -> true // Always unlocked
            "number_recognition" -> true // Always unlocked
            "addition" -> {
                // Unlock after completing some counting exercises
                val countingProgress = _moduleProgress.value?.get("counting")
                countingProgress?.completedExercises ?: 0 >= 3
            }
            "subtraction" -> {
                // Unlock after completing some addition exercises
                val additionProgress = _moduleProgress.value?.get("addition")
                additionProgress?.completedExercises ?: 0 >= 3
            }
            else -> false
        }
    }

    /**
     * Get progress for a specific module
     */
    fun getModuleProgress(moduleId: String): ProgressEntity? {
        return _moduleProgress.value?.get(moduleId)
    }

    /**
     * Update progress for a module
     */
    fun updateModuleProgress(moduleId: String, exercisesCompleted: Int, starsEarned: Int) {
        viewModelScope.launch {
            val currentProgress = _moduleProgress.value?.toMutableMap() ?: mutableMapOf()
            val moduleProgress = currentProgress[moduleId] ?: return@launch
            
            val updatedProgress = moduleProgress.copy(
                completedExercises = exercisesCompleted,
                starsEarned = starsEarned,
                lastPlayed = System.currentTimeMillis(),
                isCompleted = exercisesCompleted >= moduleProgress.totalExercises
            )
            
            currentProgress[moduleId] = updatedProgress
            _moduleProgress.value = currentProgress
            
            // Recalculate total progress
            calculateTotalProgress()
            
            // Update total stars
            val totalStars = currentProgress.values.sumOf { it.starsEarned }
            _totalStars.value = totalStars
            
            // TODO: Save to database
        }
    }

    /**
     * Add stars to total count
     */
    fun addStars(stars: Int) {
        val currentStars = _totalStars.value ?: 0
        _totalStars.value = currentStars + stars
        
        // TODO: Save to database
    }

    /**
     * Update daily streak
     */
    fun updateDailyStreak() {
        viewModelScope.launch {
            val currentStats = _userStats.value ?: return@launch
            val today = System.currentTimeMillis()
            val lastPlay = currentStats.lastPlayDate
            val oneDayMs = 24 * 60 * 60 * 1000L
            
            val newStreak = if (lastPlay == 0L) {
                // First time playing
                1
            } else {
                val daysDiff = (today - lastPlay) / oneDayMs
                when {
                    daysDiff <= 1 -> {
                        // Same day or consecutive day
                        if (daysDiff == 1L) currentStats.dailyStreak + 1 else currentStats.dailyStreak
                    }
                    else -> {
                        // Streak broken
                        1
                    }
                }
            }
            
            val updatedStats = currentStats.copy(
                dailyStreak = newStreak,
                longestStreak = maxOf(newStreak, currentStats.longestStreak),
                lastPlayDate = today
            )
            
            _userStats.value = updatedStats
            _dailyStreak.value = newStreak
            
            // TODO: Save to database
        }
    }

    /**
     * Refresh all data
     */
    fun refreshProgress() {
        loadUserData()
    }

    /**
     * Get achievement progress
     */
    fun getAchievementProgress(achievementType: String): Int {
        return when (achievementType) {
            "counting_master" -> _moduleProgress.value?.get("counting")?.completedExercises ?: 0
            "addition_expert" -> _moduleProgress.value?.get("addition")?.completedExercises ?: 0
            "subtraction_star" -> _moduleProgress.value?.get("subtraction")?.completedExercises ?: 0
            "number_detective" -> _moduleProgress.value?.get("number_recognition")?.completedExercises ?: 0
            "daily_learner" -> _dailyStreak.value ?: 0
            else -> 0
        }
    }

    /**
     * Check if achievement is unlocked
     */
    fun isAchievementUnlocked(achievementType: String, requirement: Int): Boolean {
        return getAchievementProgress(achievementType) >= requirement
    }
}
