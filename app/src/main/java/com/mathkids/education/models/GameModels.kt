package com.mathkids.education.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * Data models for the MathKids educational game
 */

/**
 * Game Module representation
 */
data class GameModule(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    @ColorRes val color: Int,
    val difficulty: Int,
    var isUnlocked: Boolean = false,
    val requiredStars: Int = 0,
    val totalLevels: Int = 10
)

/**
 * Base Exercise interface
 */
interface Exercise {
    val id: String
    val instruction: String
    val correctAnswer: Int
    val answers: List<Int>
}

/**
 * Counting Exercise
 */
data class CountingExercise(
    override val id: String,
    val difficulty: Int,
    val objectCount: Int,
    val objectType: String,
    override val answers: List<Int>,
    override val correctAnswer: Int,
    override val instruction: String,
    val objectPositions: List<Position> = emptyList()
) : Exercise

/**
 * Addition Exercise
 */
data class AdditionExercise(
    override val id: String,
    val level: Int,
    val number1: Int,
    val number2: Int,
    val operator: String,
    override val correctAnswer: Int,
    override val answers: List<Int>,
    override val instruction: String,
    val visualAid: VisualAid? = null
) : Exercise

/**
 * Subtraction Exercise
 */
data class SubtractionExercise(
    override val id: String,
    val level: Int,
    val minuend: Int,
    val subtrahend: Int,
    val operator: String,
    override val correctAnswer: Int,
    override val answers: List<Int>,
    override val instruction: String,
    val visualAid: VisualAid? = null
) : Exercise

/**
 * Number Recognition Exercise
 */
data class NumberRecognitionExercise(
    override val id: String,
    val range: Int,
    val targetNumber: Int,
    val exerciseType: String, // "identify", "sequence", "compare", "match"
    override val answers: List<Int>,
    override val correctAnswer: Int,
    override val instruction: String,
    val visualRepresentation: String? = null,
    val sequence: List<Int> = emptyList(),
    val compareNumber: Int? = null
) : Exercise

/**
 * Visual Aid for math problems
 */
data class VisualAid(
    val type: String, // "objects", "numbers", "dots"
    val objects1: List<String> = emptyList(),
    val objects2: List<String> = emptyList(),
    val operator: String = ""
)

/**
 * Position for object placement
 */
data class Position(
    val x: Float,
    val y: Float,
    val rotation: Float = 0f,
    val scale: Float = 1f
)

/**
 * Exercise Result
 */
data class ExerciseResult(
    val exerciseId: String,
    val moduleId: String,
    val isCorrect: Boolean,
    val timeSpent: Long, // in milliseconds
    val attempts: Int,
    val hintsUsed: Int,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Progress Data
 */
data class ProgressData(
    val moduleId: String,
    val level: Int,
    val starsEarned: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val averageTime: Long,
    val accuracy: Float, // percentage
    val lastPlayed: Long = System.currentTimeMillis()
)

/**
 * Achievement
 */
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    val type: AchievementType,
    val requirement: Int,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null
)

/**
 * Achievement Types
 */
enum class AchievementType {
    COUNTING_MASTER,
    ADDITION_EXPERT,
    SUBTRACTION_STAR,
    NUMBER_DETECTIVE,
    DAILY_LEARNER,
    SPEED_DEMON,
    PERFECTIONIST,
    PERSISTENT_LEARNER
}

/**
 * User Statistics
 */
data class UserStats(
    val totalStars: Int = 0,
    val totalExercises: Int = 0,
    val correctAnswers: Int = 0,
    val totalPlayTime: Long = 0, // in milliseconds
    val dailyStreak: Int = 0,
    val longestStreak: Int = 0,
    val favoriteModule: String? = null,
    val averageAccuracy: Float = 0f,
    val lastPlayDate: Long = 0
)

/**
 * Game Settings
 */
data class GameSettings(
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val vibrateEnabled: Boolean = true,
    val speechRate: Float = 0.8f,
    val speechPitch: Float = 1.1f,
    val difficultyLevel: Int = 1,
    val language: String = "vi",
    val parentalControlsEnabled: Boolean = false,
    val maxPlayTimeMinutes: Int = 30
)

/**
 * Daily Challenge
 */
data class DailyChallenge(
    val id: String,
    val date: String, // YYYY-MM-DD format
    val moduleId: String,
    val exercises: List<Exercise>,
    val rewardStars: Int,
    val isCompleted: Boolean = false,
    val completionTime: Long? = null
)

/**
 * Leaderboard Entry (for family/local competition)
 */
data class LeaderboardEntry(
    val playerName: String,
    val totalStars: Int,
    val totalExercises: Int,
    val accuracy: Float,
    val lastUpdated: Long
)

/**
 * Hint System
 */
data class Hint(
    val type: HintType,
    val content: String,
    val visualAid: String? = null,
    val audioInstruction: String? = null
)

/**
 * Hint Types
 */
enum class HintType {
    VISUAL_HIGHLIGHT,
    AUDIO_INSTRUCTION,
    STEP_BY_STEP,
    EXAMPLE_PROBLEM,
    COUNTING_AID
}

/**
 * Animation Configuration
 */
data class AnimationConfig(
    val type: AnimationType,
    val duration: Long = 300,
    val delay: Long = 0,
    val repeatCount: Int = 0,
    val interpolator: String = "ease_in_out"
)

/**
 * Animation Types
 */
enum class AnimationType {
    FADE_IN,
    FADE_OUT,
    SLIDE_IN,
    SLIDE_OUT,
    BOUNCE,
    SCALE,
    ROTATE,
    CELEBRATION
}

/**
 * Sound Effect Configuration
 */
data class SoundEffect(
    val name: String,
    val resourceId: Int,
    val volume: Float = 1.0f,
    val loop: Boolean = false
)

/**
 * Theme Configuration
 */
data class ThemeConfig(
    val name: String,
    val primaryColor: Int,
    val secondaryColor: Int,
    val backgroundColor: Int,
    val textColor: Int,
    val buttonStyle: String = "rounded"
)

/**
 * Parental Report
 */
data class ParentalReport(
    val childName: String,
    val reportDate: String,
    val totalPlayTime: Long,
    val exercisesCompleted: Int,
    val accuracy: Float,
    val strongAreas: List<String>,
    val improvementAreas: List<String>,
    val recommendations: List<String>
)
