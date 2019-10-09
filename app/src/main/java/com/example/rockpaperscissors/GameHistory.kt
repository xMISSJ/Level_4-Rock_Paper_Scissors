package com.example.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class GameHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Your Game History"
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
                Toast.makeText(this, "Delete", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
