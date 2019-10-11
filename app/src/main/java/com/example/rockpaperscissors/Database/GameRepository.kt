package com.example.rockpaperscissors.Database

import android.content.Context
import com.example.rockpaperscissors.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)

    suspend fun deleteGame(game: Game) = gameDao.deleteGame(game)

    suspend fun updateGame(game: Game) = gameDao.updateGame(game)

}
