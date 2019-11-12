package com.example.rockpaperscissors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (

    @ColumnInfo(name = "game")
    var winnerText: String,
    var date: String,
    var playerImage: Int,
    var computerImage: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)

// How is the game result stored in the database.