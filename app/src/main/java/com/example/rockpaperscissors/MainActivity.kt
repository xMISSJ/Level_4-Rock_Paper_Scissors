package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val IMAGES_LIST = arrayListOf<Image>((Image("Rock", R.drawable.rock)),
                                          Image("Paper", R.drawable.paper),
                                          Image("Scissors", R.drawable.scissors))
    var random = Random()
    var statistics = Outcome(0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        tvStatistics.text = statistics.toString()

        ibRock.setOnClickListener { onClick(ibRock) }
        ibPaper.setOnClickListener { onClick(ibPaper) }
        ibScissors.setOnClickListener { onClick(ibScissors) }
    }

    private fun onClick(button: ImageButton){

        var computerChoice = IMAGES_LIST[random.nextInt(IMAGES_LIST.size)]
        var playerChoice: String

        when (button) {
            ibRock -> {
                // Rock
                ivPlayer.setImageResource(IMAGES_LIST[0].image)
                playerChoice = IMAGES_LIST[0].imageid
                getResult(Choice(playerChoice, computerChoice.imageid))
            }
            ibPaper -> {
                // Paper
                ivPlayer.setImageResource(IMAGES_LIST[1].image)
                playerChoice = IMAGES_LIST[1].imageid
                getResult(Choice(playerChoice, computerChoice.imageid))
            }
            ibScissors -> {
                // Scissors
                ivPlayer.setImageResource(IMAGES_LIST[2].image)
                playerChoice = IMAGES_LIST[2].imageid
                getResult(Choice(playerChoice, computerChoice.imageid))
            }
        }
        ivComputer.setImageResource(computerChoice.image)
    }

    private fun getResult(input : Choice){

        // Player wins.
        if ((input.playerChoice == "Rock" && input.computerChoice == "Scissors") ||
            (input.playerChoice == "Paper" && input.computerChoice == "Rock") ||
            (input.playerChoice == "Scissors" && input.computerChoice == "Paper")) {
            statistics.win++
        } else if (input.playerChoice == input.computerChoice) {
            statistics.draw++
        } else if ((input.playerChoice == "Rock" && input.computerChoice == "Paper") ||             // Rock vs. Paper.
                   (input.playerChoice == "Paper" && input.computerChoice == "Scissors") ||             // Paper vs. Scissors.
                   (input.playerChoice == "Scissors" && input.computerChoice == "Rock")) {             // Scissors vs. Rock.
            statistics.lose++
        } else {
            Toast.makeText(this, "None", Toast.LENGTH_SHORT).show()
        }
        tvStatistics.text = statistics.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (id) {
            R.id.action_history -> {
                val intent = Intent(this@MainActivity, GameHistory::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    data class Choice (var playerChoice: String, var computerChoice: String)
    data class Outcome (var win: Int, var draw: Int, var lose: Int)
    data class Image (var imageid: String, var image: Int)
}
