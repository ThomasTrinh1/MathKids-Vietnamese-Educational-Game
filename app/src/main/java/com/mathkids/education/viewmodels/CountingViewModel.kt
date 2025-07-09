package com.mathkids.education.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathkids.education.MathKidsApplication
import com.mathkids.education.models.CountingExercise
import com.mathkids.education.models.ExerciseResult
import kotlinx.coroutines.launch

/**
 * ViewModel for CountingActivity
 * Manages counting exercises, scoring, and progress tracking
 */
class CountingViewModel : ViewModel() {

    private val _currentExercise = MutableLiveData<CountingExercise>()
    val currentExercise: LiveData<CountingExercise> = _currentExercise

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val _level = MutableLiveData<Int>()
    val level: LiveData<Int> = _level

    private val _isCorrect = MutableLiveData<Boolean?>()
    val isCorrect: LiveData<Boolean?> = _isCorrect

    private val _exerciseCount = MutableLiveData<Int>()
    val exerciseCount: LiveData<Int> = _exerciseCount

    private val _starsEarned = MutableLiveData<Int>()
    val starsEarned: LiveData<Int> = _starsEarned

    // Game state
    private var currentDifficulty = 1
    private var exerciseStartTime = 0L
    private var attempts = 0
    private var hintsUsed = 0
    private var totalExercises = 0
    private var correctAnswers = 0

    init {
        initializeGame()
    }

    private fun initializeGame() {
        _score.value = 0
        _level.value = 1
        _exerciseCount.value = 0
        _starsEarned.value = 0
        currentDifficulty = 1
        totalExercises = 0
        correctAnswers = 0
    }

    /**
     * Generate a new counting exercise
     */
    fun generateNewExercise() {
        viewModelScope.launch {
            try {
                // Get content generator from application
                val app = MathKidsApplication.getInstance(null) // TODO: Pass context properly
                val exercise = app.contentGenerator.generateCountingExercise(currentDifficulty)
                
                _currentExercise.value = exercise
                _isCorrect.value = null
                
                // Reset exercise state
                exerciseStartTime = System.currentTimeMillis()
                attempts = 0
                hintsUsed = 0
                
                // Increment exercise count
                totalExercises++
                _exerciseCount.value = totalExercises
                
            } catch (e: Exception) {
                e.printStackTrace()
                // Generate a simple fallback exercise
                generateFallbackExercise()
            }
        }
    }

    private fun generateFallbackExercise() {
        val objectCount = (1..5).random()
        val wrongAnswers = generateSimpleWrongAnswers(objectCount)
        val allAnswers = (wrongAnswers + objectCount).shuffled()
        
        val exercise = CountingExercise(
            id = "fallback_${System.currentTimeMillis()}",
            difficulty = 1,
            objectCount = objectCount,
            objectType = "circle",
            answers = allAnswers,
            correctAnswer = objectCount,
            instruction = "Đếm các hình tròn trên màn hình"
        )
        
        _currentExercise.value = exercise
    }

    private fun generateSimpleWrongAnswers(correct: Int): List<Int> {
        val wrong = mutableSetOf<Int>()
        while (wrong.size < 3) {
            val num = (1..10).random()
            if (num != correct) {
                wrong.add(num)
            }
        }
        return wrong.toList()
    }

    /**
     * Check if the selected answer is correct
     */
    fun checkAnswer(selectedAnswer: Int) {
        val exercise = _currentExercise.value ?: return
        val isAnswerCorrect = selectedAnswer == exercise.correctAnswer
        
        attempts++
        val timeSpent = System.currentTimeMillis() - exerciseStartTime
        
        _isCorrect.value = isAnswerCorrect
        
        if (isAnswerCorrect) {
            handleCorrectAnswer(timeSpent)
        } else {
            handleIncorrectAnswer()
        }
        
        // Save exercise result
        saveExerciseResult(exercise, isAnswerCorrect, timeSpent)
    }

    private fun handleCorrectAnswer(timeSpent: Long) {
        correctAnswers++
        
        // Calculate stars based on performance
        val stars = calculateStars(timeSpent, attempts, hintsUsed)
        val currentStars = _starsEarned.value ?: 0
        _starsEarned.value = currentStars + stars
        
        // Calculate score
        val points = calculatePoints(stars, currentDifficulty)
        val currentScore = _score.value ?: 0
        _score.value = currentScore + points
        
        // Check if should increase difficulty
        checkDifficultyIncrease()
        
        // Check if should increase level
        checkLevelIncrease()
    }

    private fun handleIncorrectAnswer() {
        // No penalty for wrong answers, just encouragement
        // Could add gentle feedback here
    }

    private fun calculateStars(timeSpent: Long, attempts: Int, hintsUsed: Int): Int {
        return when {
            attempts == 1 && hintsUsed == 0 && timeSpent < 10000 -> 3 // Perfect: first try, no hints, fast
            attempts == 1 && hintsUsed == 0 -> 2 // Good: first try, no hints
            attempts <= 2 && hintsUsed <= 1 -> 1 // Okay: few attempts, maybe one hint
            else -> 1 // Participation star
        }
    }

    private fun calculatePoints(stars: Int, difficulty: Int): Int {
        return stars * 10 * difficulty
    }

    private fun checkDifficultyIncrease() {
        // Increase difficulty every 3 correct answers
        if (correctAnswers % 3 == 0 && currentDifficulty < 4) {
            currentDifficulty++
        }
    }

    private fun checkLevelIncrease() {
        // Increase level every 5 exercises
        if (totalExercises % 5 == 0) {
            val currentLevel = _level.value ?: 1
            _level.value = currentLevel + 1
        }
    }

    private fun saveExerciseResult(exercise: CountingExercise, isCorrect: Boolean, timeSpent: Long) {
        viewModelScope.launch {
            try {
                val result = ExerciseResult(
                    exerciseId = exercise.id,
                    moduleId = "counting",
                    isCorrect = isCorrect,
                    timeSpent = timeSpent,
                    attempts = attempts,
                    hintsUsed = hintsUsed
                )
                
                // TODO: Save to database
                // app.database.exerciseResultDao().insert(result)
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Use a hint
     */
    fun useHint() {
        hintsUsed++
        // Hint logic is handled in the Activity
    }

    /**
     * Get current progress percentage
     */
    fun getProgress(): Int {
        val target = 10 // Target exercises per session
        val completed = totalExercises
        return minOf(100, (completed * 100) / target)
    }

    /**
     * Get current accuracy
     */
    fun getAccuracy(): Float {
        return if (totalExercises > 0) {
            (correctAnswers.toFloat() / totalExercises.toFloat()) * 100f
        } else {
            0f
        }
    }

    /**
     * Get session statistics
     */
    fun getSessionStats(): Map<String, Any> {
        return mapOf(
            "totalExercises" to totalExercises,
            "correctAnswers" to correctAnswers,
            "accuracy" to getAccuracy(),
            "starsEarned" to (_starsEarned.value ?: 0),
            "finalScore" to (_score.value ?: 0),
            "finalLevel" to (_level.value ?: 1)
        )
    }

    /**
     * Reset game state
     */
    fun resetGame() {
        initializeGame()
    }

    /**
     * Set difficulty level
     */
    fun setDifficulty(difficulty: Int) {
        currentDifficulty = difficulty.coerceIn(1, 4)
    }

    /**
     * Get current difficulty
     */
    fun getCurrentDifficulty(): Int = currentDifficulty
}
