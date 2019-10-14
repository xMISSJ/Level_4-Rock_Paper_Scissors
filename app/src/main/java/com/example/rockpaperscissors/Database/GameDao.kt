package com.example.rockpaperscissors.Database

import androidx.room.*
import com.example.rockpaperscissors.Game

/*
 * This set of Dao objects forms the main component of Room,
 * as each DAO includes methods that offer abstract access to your app's database.
 * Declare all needed methods for the database.
 */
@Dao
interface GameDao {

    // CRUD already exists. Use Query for personal additions.
    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()
}