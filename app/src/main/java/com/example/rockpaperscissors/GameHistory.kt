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

class GameHistory : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository

    var gamesList = arrayListOf<Game>()
    var gameAdapter = GameAdapter(gamesList)

    var WINNERDISPLAY_LIST = arrayListOf<String>()
    var DATETIME_LIST = arrayListOf<String>()
    var PLAYERCHOICE_LIST = arrayListOf<Int>()
    var COMPUTERCHOICE_LIST = arrayListOf<Int>()

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
        WINNERDISPLAY_LIST = intent.getStringArrayListExtra("WINNERDISPLAY_LIST")
        DATETIME_LIST = intent.getStringArrayListExtra("DATETIME_LIST")
        PLAYERCHOICE_LIST = intent.getIntegerArrayListExtra("PLAYERCHOICE_LIST")
        COMPUTERCHOICE_LIST = intent.getIntegerArrayListExtra("COMPUTERCHOICE_LIST")

        rvGames.layoutManager = LinearLayoutManager(this@GameHistory, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
        rvGames.addItemDecoration(DividerItemDecoration(this@GameHistory, DividerItemDecoration.VERTICAL))

        for (i in WINNERDISPLAY_LIST.indices) {
            gamesList.add(Game(WINNERDISPLAY_LIST[i], DATETIME_LIST[i], PLAYERCHOICE_LIST[i], COMPUTERCHOICE_LIST[i]))
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
                // For future purposes.
                MainActivity().IMAGES_LIST.clear()

                gamesList.clear()
                gameAdapter.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
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
