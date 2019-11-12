package com.example.rockpaperscissors

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.parcel.Parcelize

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    val IMAGES_LIST = arrayListOf((Image("Rock", R.drawable.rock)),
                                   Image("Paper", R.drawable.paper),
                                   Image("Scissors", R.drawable.scissors))

    private var random = Random()
    private var statistics = Outcome(0, 0, 0)

    var winnerDisplayList = arrayListOf<String>()
    var dateTimeList = arrayListOf<String>()
    var playerChoiceList = arrayListOf<Int>()
    var computerChoiceList = arrayListOf<Int>()

    var playerImageChoice = 0
    var computerImageChoice = 0
    var round = 0

    lateinit var currentDateTime: LocalDateTime
    lateinit var timeZone: ZonedDateTime
    lateinit var dateTimeFormatter: DateTimeFormatter
    lateinit var dateTimeValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        tvStatistics.text = getString(R.string.statistics_text, statistics.win, statistics.draw, statistics.lose)
        tvResult.text = null

        ibRock.setOnClickListener { onClick(ibRock) }
        ibPaper.setOnClickListener { onClick(ibPaper) }
        ibScissors.setOnClickListener { onClick(ibScissors) }
    }

    private fun onClick(button: ImageButton){

        // Increase round on image click.
        round++

        var randomComputerChoice = IMAGES_LIST[random.nextInt(IMAGES_LIST.size)]
        var playerTextChoice: String

        when (button) {
            ibRock -> {
                // Rock
                ivPlayer.setImageResource(IMAGES_LIST[0].image)
                playerTextChoice = IMAGES_LIST[0].imageId
                playerImageChoice = IMAGES_LIST[0].image
                setResult(Choice(playerTextChoice, randomComputerChoice.imageId))
            }
            ibPaper -> {
                // Paper
                ivPlayer.setImageResource(IMAGES_LIST[1].image)
                playerTextChoice = IMAGES_LIST[1].imageId
                playerImageChoice = IMAGES_LIST[1].image
                setResult(Choice(playerTextChoice, randomComputerChoice.imageId))
            }
            ibScissors -> {
                // Scissors
                ivPlayer.setImageResource(IMAGES_LIST[2].image)
                playerTextChoice = IMAGES_LIST[2].imageId
                playerImageChoice = IMAGES_LIST[2].image
                setResult(Choice(playerTextChoice, randomComputerChoice.imageId))
            }
        }
        ivComputer.setImageResource(randomComputerChoice.image)
        computerImageChoice = randomComputerChoice.image

        // These two will be used to pass to game history activity.
        playerChoiceList.add(playerImageChoice)
        computerChoiceList.add(computerImageChoice)
    }

    private fun setResult(input : Choice){

        // Debug purposes.
        //Toast.makeText(this, round.toString(), Toast.LENGTH_SHORT).show()

        val WIN = "You win!"
        val DRAW = "It's a draw."
        val LOSE = "You've lost..."

        val GH_WIN = "Player wins!"
        val GH_DRAW = "Draw"
        val GH_LOSE = "Computer wins!"

        // Player wins.
        if ((input.playerChoice == "Rock" && input.computerChoice == "Scissors") ||
            (input.playerChoice == "Paper" && input.computerChoice == "Rock") ||
            (input.playerChoice == "Scissors" && input.computerChoice == "Paper")) {
            statistics.win++
            tvResult.text = getString(R.string.result_text, WIN)
            winnerDisplayList.add(GH_WIN)
        // Draw.
        } else if (input.playerChoice == input.computerChoice) {
            statistics.draw++
            tvResult.text = getString(R.string.result_text, DRAW)
            winnerDisplayList.add(GH_DRAW)
        // Player lost.
        } else if ((input.playerChoice == "Rock" && input.computerChoice == "Paper") ||
                   (input.playerChoice == "Paper" && input.computerChoice == "Scissors") ||
                   (input.playerChoice == "Scissors" && input.computerChoice == "Rock")) {
            statistics.lose++
            tvResult.text = getString(R.string.result_text, LOSE)
            winnerDisplayList.add(GH_LOSE)
        }
        tvStatistics.text = getString(R.string.statistics_text, statistics.win, statistics.draw, statistics.lose)
        dateTimeList.add(setDateTime())
    }

    private fun setDateTime() : String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now()
            timeZone = ZonedDateTime.of(currentDateTime, ZoneId.of("Europe/Paris"))
            dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss ZZZZ yyyy")
            dateTimeValue =  timeZone.format(dateTimeFormatter)
            return dateTimeValue
        } else return "None"
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
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                // Pass data to next activity.
                intent.putExtra("winnerDisplayList", winnerDisplayList)
                intent.putExtra("dateTimeList", dateTimeList)
                intent.putExtra("computerChoiceList", computerChoiceList)
                intent.putExtra("playerChoiceList", playerChoiceList)
                intent.putExtra("statistics", statistics)
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    data class Choice (var playerChoice: String, var computerChoice: String)
    @Parcelize data class Outcome (var win: Int, var draw: Int, var lose: Int): Parcelable
    data class Image (var imageId: String, var image: Int)
}
