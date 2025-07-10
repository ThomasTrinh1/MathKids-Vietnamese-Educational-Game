package com.mathkids.education.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mathkids.education.database.entities.UserStatsEntity

/**
 * Data Access Object for UserStats operations
 */
@Dao
interface UserStatsDao {

    @Query("SELECT * FROM user_stats WHERE id = 1")
    fun getUserStats(): LiveData<UserStatsEntity?>

    @Query("SELECT * FROM user_stats WHERE id = 1")
    suspend fun getUserStatsSync(): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStats(userStats: UserStatsEntity): Long

    @Update
    suspend fun updateUserStats(userStats: UserStatsEntity): Int

    @Query("UPDATE user_stats SET totalStars = totalStars + :stars WHERE id = 1")
    suspend fun addStars(stars: Int): Int

    @Query("UPDATE user_stats SET totalExercises = totalExercises + 1 WHERE id = 1")
    suspend fun incrementTotalExercises(): Int

    @Query("UPDATE user_stats SET correctAnswers = correctAnswers + 1 WHERE id = 1")
    suspend fun incrementCorrectAnswers(): Int

    @Query("UPDATE user_stats SET totalPlayTime = totalPlayTime + :playTime WHERE id = 1")
    suspend fun addPlayTime(playTime: Long): Int

    @Query("UPDATE user_stats SET dailyStreak = :streak WHERE id = 1")
    suspend fun updateDailyStreak(streak: Int): Int

    @Query("UPDATE user_stats SET longestStreak = :streak WHERE id = 1")
    suspend fun updateLongestStreak(streak: Int): Int

    @Query("UPDATE user_stats SET favoriteModule = :moduleId WHERE id = 1")
    suspend fun updateFavoriteModule(moduleId: String): Int

    @Query("UPDATE user_stats SET averageAccuracy = :accuracy WHERE id = 1")
    suspend fun updateAverageAccuracy(accuracy: Float): Int

    @Query("UPDATE user_stats SET lastPlayDate = :date WHERE id = 1")
    suspend fun updateLastPlayDate(date: Long): Int

    @Query("UPDATE user_stats SET totalDaysPlayed = totalDaysPlayed + 1 WHERE id = 1")
    suspend fun incrementTotalDaysPlayed(): Int
}
