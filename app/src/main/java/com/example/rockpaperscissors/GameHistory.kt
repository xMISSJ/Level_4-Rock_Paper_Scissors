package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.Database.GameRepository
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistory : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository

    var gamesList = arrayListOf<Game>()
    private var gameAdapter = GameAdapter(gamesList)

    private var winnerDisplayList = arrayListOf<String>()
    private var dateTimeList = arrayListOf<String>()
    var playerChoiceList = arrayListOf<Int>()
    var computerChoiceList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Your Game History"

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews(){
        // intent.get<something>extra for other options.
        winnerDisplayList = intent.getStringArrayListExtra("winnerDisplayList")
        dateTimeList = intent.getStringArrayListExtra("dateTimeList")
        playerChoiceList = intent.getIntegerArrayListExtra("playerChoiceList")
        computerChoiceList = intent.getIntegerArrayListExtra("computerChoiceList")

        rvGames.layoutManager = LinearLayoutManager(this@GameHistory, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
        rvGames.addItemDecoration(DividerItemDecoration(this@GameHistory, DividerItemDecoration.VERTICAL))

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                for (i in winnerDisplayList.indices) {
                    //gamesList.add(Game(winnerDisplayList[i], dateTimeList[i], playerChoiceList[i], computerChoiceList[i]))
                    // This adds the game to the database. This way game history won't be empty when closing the activity.
                    gameRepository.insertGame(Game(winnerDisplayList[i], dateTimeList[i], playerChoiceList[i], computerChoiceList[i]))
                }
                getGamesFromDatabase()
            }
        }

        getGamesFromDatabase()
    }

    private fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameHistory.gamesList.clear()
            this@GameHistory.gamesList.addAll(games)
            this@GameHistory.gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_game_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (id) {
            R.id.action_delete -> {
                deleteGameHistory()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteGameHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val previousIntent = Intent(this@GameHistory, MainActivity::class.java)
        // If the activity already exists: instead of launching a new instance of this activity, all of the other activities on top of it will be closed.
        // his Intent will be delivered to the (now on top) old activity as a new Intent.
        previousIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(previousIntent)
        finish()
        return true
    }
}
