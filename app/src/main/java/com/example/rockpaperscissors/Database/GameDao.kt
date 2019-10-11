package com.example.rockpaperscissors.Database

import androidx.room.*
import com.example.rockpaperscissors.Game

@Dao
interface GameDao {
    @Query("SELECT COUNT(*) FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)
}