package com.mathkids.education.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entities for MathKids application
 */

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var moduleId: String,
    var level: Int,
    var starsEarned: Int,
    var totalExercises: Int,
    var completedExercises: Int,
    var averageTime: Long,
    var accuracy: Float,
    var lastPlayed: Long = System.currentTimeMillis(),
    var isCompleted: Boolean = false
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    var id: String,
    var title: String,
    var description: String,
    var iconResId: Int,
    var type: String,
    var requirement: Int,
    var currentProgress: Int = 0,
    var isUnlocked: Boolean = false,
    var unlockedDate: Long? = null
)

@Entity(tableName = "exercise_results")
data class ExerciseResultEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var exerciseId: String,
    var moduleId: String,
    var exerciseType: String,
    var isCorrect: Boolean,
    var timeSpent: Long,
    var attempts: Int,
    var hintsUsed: Int,
    var difficulty: Int,
    var timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey
    var id: Int = 1, // Single row for user stats
    var totalStars: Int = 0,
    var totalExercises: Int = 0,
    var correctAnswers: Int = 0,
    var totalPlayTime: Long = 0,
    var dailyStreak: Int = 0,
    var longestStreak: Int = 0,
    var favoriteModule: String? = null,
    var averageAccuracy: Float = 0f,
    var lastPlayDate: Long = 0,
    var totalDaysPlayed: Int = 0
)

@Entity(tableName = "game_settings")
data class GameSettingsEntity(
    @PrimaryKey
    var id: Int = 1, // Single row for settings
    var soundEnabled: Boolean = true,
    var musicEnabled: Boolean = true,
    var vibrateEnabled: Boolean = true,
    var speechRate: Float = 0.8f,
    var speechPitch: Float = 1.1f,
    var difficultyLevel: Int = 1,
    var language: String = "vi",
    var parentalControlsEnabled: Boolean = false,
    var maxPlayTimeMinutes: Int = 30,
    var isFirstLaunch: Boolean = true
)
