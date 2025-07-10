package com.mathkids.education.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mathkids.education.database.entities.ExerciseResultEntity

/**
 * Data Access Object for ExerciseResult operations
 */
@Dao
interface ExerciseResultDao {

    @Query("SELECT * FROM exercise_results ORDER BY timestamp DESC")
    fun getAllResults(): LiveData<List<ExerciseResultEntity>>

    @Query("SELECT * FROM exercise_results WHERE moduleId = :moduleId ORDER BY timestamp DESC")
    fun getResultsByModule(moduleId: String): LiveData<List<ExerciseResultEntity>>

    @Query("SELECT * FROM exercise_results WHERE exerciseId = :exerciseId")
    suspend fun getResultsByExercise(exerciseId: String): List<ExerciseResultEntity>

    @Insert
    suspend fun insertResult(result: ExerciseResultEntity): Long

    @Insert
    suspend fun insertResults(results: List<ExerciseResultEntity>): List<Long>

    @Update
    suspend fun updateResult(result: ExerciseResultEntity): Int

    @Delete
    suspend fun deleteResult(result: ExerciseResultEntity): Int

    @Query("DELETE FROM exercise_results WHERE moduleId = :moduleId")
    suspend fun deleteResultsByModule(moduleId: String)

    @Query("SELECT COUNT(*) FROM exercise_results WHERE moduleId = :moduleId AND isCorrect = 1")
    suspend fun getCorrectAnswersCount(moduleId: String): Int

    @Query("SELECT COUNT(*) FROM exercise_results WHERE moduleId = :moduleId")
    suspend fun getTotalAnswersCount(moduleId: String): Int

    @Query("SELECT AVG(timeSpent) FROM exercise_results WHERE moduleId = :moduleId AND isCorrect = 1")
    suspend fun getAverageTimeSpent(moduleId: String): Long

    @Query("SELECT * FROM exercise_results WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    suspend fun getResultsSince(startTime: Long): List<ExerciseResultEntity>

    @Query("SELECT COUNT(*) FROM exercise_results WHERE timestamp >= :startTime AND timestamp <= :endTime")
    suspend fun getResultsCountInPeriod(startTime: Long, endTime: Long): Int
}
