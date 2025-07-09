package com.mathkids.education.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entities for MathKids application
 */

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val moduleId: String,
    val level: Int,
    val starsEarned: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val averageTime: Long,
    val accuracy: Float,
    val lastPlayed: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val iconResId: Int,
    val type: String,
    val requirement: Int,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null
)

@Entity(tableName = "exercise_results")
data class ExerciseResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseId: String,
    val moduleId: String,
    val exerciseType: String,
    val isCorrect: Boolean,
    val timeSpent: Long,
    val attempts: Int,
    val hintsUsed: Int,
    val difficulty: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey
    val id: Int = 1, // Single row for user stats
    val totalStars: Int = 0,
    val totalExercises: Int = 0,
    val correctAnswers: Int = 0,
    val totalPlayTime: Long = 0,
    val dailyStreak: Int = 0,
    val longestStreak: Int = 0,
    val favoriteModule: String? = null,
    val averageAccuracy: Float = 0f,
    val lastPlayDate: Long = 0,
    val totalDaysPlayed: Int = 0
)

@Entity(tableName = "game_settings")
data class GameSettingsEntity(
    @PrimaryKey
    val id: Int = 1, // Single row for settings
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val vibrateEnabled: Boolean = true,
    val speechRate: Float = 0.8f,
    val speechPitch: Float = 1.1f,
    val difficultyLevel: Int = 1,
    val language: String = "vi",
    val parentalControlsEnabled: Boolean = false,
    val maxPlayTimeMinutes: Int = 30,
    val isFirstLaunch: Boolean = true
)
