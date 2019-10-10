package com.example.rockpaperscissors

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game (
    var winnerText: String,
    var date: String,
    var playerImage: Int,
    var computerImage: Int
) : Parcelable