package com.example.rockpaperscissors.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rockpaperscissors.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)

abstract class GameRoomDatabase : RoomDatabase(){
    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        // Essentially, volatile is used to indicate that a variable's value will be modified by different threads.
        @Volatile
        private var gameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (gameRoomDatabaseInstance == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (gameRoomDatabaseInstance == null) {
                        gameRoomDatabaseInstance = Room.databaseBuilder(context.applicationContext, GameRoomDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
                        }
                    }
                }
            return gameRoomDatabaseInstance
            }
        }
    }
}