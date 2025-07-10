package com.mathkids.education.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mathkids.education.database.entities.GameSettingsEntity

/**
 * Data Access Object for GameSettings operations
 */
@Dao
interface GameSettingsDao {

    @Query("SELECT * FROM game_settings WHERE id = 1")
    fun getGameSettings(): LiveData<GameSettingsEntity?>

    @Query("SELECT * FROM game_settings WHERE id = 1")
    suspend fun getGameSettingsSync(): GameSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameSettings(settings: GameSettingsEntity): Long

    @Update
    suspend fun updateGameSettings(settings: GameSettingsEntity): Int

    @Query("UPDATE game_settings SET soundEnabled = :enabled WHERE id = 1")
    suspend fun updateSoundEnabled(enabled: Boolean): Int

    @Query("UPDATE game_settings SET musicEnabled = :enabled WHERE id = 1")
    suspend fun updateMusicEnabled(enabled: Boolean): Int

    @Query("UPDATE game_settings SET vibrateEnabled = :enabled WHERE id = 1")
    suspend fun updateVibrateEnabled(enabled: Boolean): Int

    @Query("UPDATE game_settings SET speechRate = :rate WHERE id = 1")
    suspend fun updateSpeechRate(rate: Float): Int

    @Query("UPDATE game_settings SET speechPitch = :pitch WHERE id = 1")
    suspend fun updateSpeechPitch(pitch: Float): Int

    @Query("UPDATE game_settings SET difficultyLevel = :level WHERE id = 1")
    suspend fun updateDifficultyLevel(level: Int): Int

    @Query("UPDATE game_settings SET language = :language WHERE id = 1")
    suspend fun updateLanguage(language: String): Int

    @Query("UPDATE game_settings SET parentalControlsEnabled = :enabled WHERE id = 1")
    suspend fun updateParentalControlsEnabled(enabled: Boolean): Int

    @Query("UPDATE game_settings SET maxPlayTimeMinutes = :minutes WHERE id = 1")
    suspend fun updateMaxPlayTimeMinutes(minutes: Int): Int

    @Query("UPDATE game_settings SET isFirstLaunch = :isFirst WHERE id = 1")
    suspend fun updateFirstLaunch(isFirst: Boolean): Int
}
