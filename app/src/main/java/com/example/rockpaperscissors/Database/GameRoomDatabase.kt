package com.example.rockpaperscissors.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rockpaperscissors.Game

/*
 * Puts together the entities and DAO('s).
 */

// Define what entities to store in our database.

@Database(entities = [Game::class], version = 1, exportSchema = false)

abstract class GameRoomDatabase : RoomDatabase() {

    // Abstract method to get the implementation room makes.
    abstract fun gameDao(): GameDao

    // We want the database to be static.
    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        // Essentially, volatile is used to indicate that a variable's value will be modified by different threads.
        @Volatile
        private var gameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (gameRoomDatabaseInstance == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (gameRoomDatabaseInstance == null) {
                        gameRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GameRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return gameRoomDatabaseInstance
        }
    }
}