package com.mathkids.education.database

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mathkids.education.database.dao.*
import com.mathkids.education.database.entities.*

/**
 * Main database for MathKids application
 * Uses Room persistence library for local data storage
 */
@Database(
    entities = [
        ProgressEntity::class,
        AchievementEntity::class,
        ExerciseResultEntity::class,
        UserStatsEntity::class,
        GameSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MathKidsDatabase : RoomDatabase() {

    abstract fun progressDao(): ProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun exerciseResultDao(): ExerciseResultDao
    abstract fun userStatsDao(): UserStatsDao
    abstract fun gameSettingsDao(): GameSettingsDao

    companion object {
        const val DATABASE_NAME = "mathkids_database"

        // Migration from version 1 to 2 (for future use)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add migration logic here when needed
            }
        }
    }
}

/**
 * Type converters for Room database
 */
class Converters {
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return if (value.isEmpty()) emptyList() else value.split(",").map { it.toInt() }
    }
}
