package com.mathkids.education.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mathkids.education.database.entities.ProgressEntity

/**
 * Data Access Object for Progress operations
 */
@Dao
interface ProgressDao {

    @Query("SELECT * FROM progress")
    fun getAllProgress(): LiveData<List<ProgressEntity>>

    @Query("SELECT * FROM progress WHERE moduleId = :moduleId")
    fun getProgressByModule(moduleId: String): LiveData<ProgressEntity?>

    @Query("SELECT * FROM progress WHERE moduleId = :moduleId")
    suspend fun getProgressByModuleSync(moduleId: String): ProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity): Long

    @Update
    suspend fun updateProgress(progress: ProgressEntity): Int

    @Delete
    suspend fun deleteProgress(progress: ProgressEntity): Int

    @Query("DELETE FROM progress WHERE moduleId = :moduleId")
    suspend fun deleteProgressByModule(moduleId: String): Int

    @Query("SELECT SUM(starsEarned) FROM progress")
    suspend fun getTotalStars(): Int?

    @Query("SELECT SUM(completedExercises) FROM progress")
    suspend fun getTotalCompletedExercises(): Int?

    @Query("SELECT AVG(accuracy) FROM progress WHERE completedExercises > 0")
    suspend fun getAverageAccuracy(): Float?

    @Query("SELECT * FROM progress WHERE isCompleted = 1")
    suspend fun getCompletedModules(): List<ProgressEntity>

    @Query("UPDATE progress SET starsEarned = starsEarned + :stars WHERE moduleId = :moduleId")
    suspend fun addStars(moduleId: String, stars: Int): Int

    @Query("UPDATE progress SET completedExercises = completedExercises + 1 WHERE moduleId = :moduleId")
    suspend fun incrementCompletedExercises(moduleId: String): Int
}
