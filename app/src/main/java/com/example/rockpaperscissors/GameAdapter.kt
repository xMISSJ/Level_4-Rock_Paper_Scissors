package com.example.rockpaperscissors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    // Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    // Returns size of the list.
    override fun getItemCount(): Int = games.size

    // Called by RecyclerView to display the data at the specified position.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            // For each item in the itemView assign their correct values.
            itemView.tvWinner.text = game.winnerText
            itemView.tvDate.text = game.date
            itemView.ivGHComputer.setImageResource(game.computerImage)
            itemView.ivGHPlayer.setImageResource(game.playerImage)
        }
    }
}