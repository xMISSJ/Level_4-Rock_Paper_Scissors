package com.example.rockpaperscissors.Database

import androidx.room.*
import com.example.rockpaperscissors.Game

@Dao
interface GameDAO {
    @Query("SELECT COUNT(*) FROM game_table")
    fun getAllGames(): List<Game>

    @Insert
    fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Update
    fun updateGame(game: Game)
}