package com.example.rockpaperscissors

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable