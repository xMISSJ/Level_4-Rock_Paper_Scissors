package com.example.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val IMAGES_LIST = arrayListOf(R.drawable.rock, R.drawable.paper, R.drawable.scissors)
    var random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        ibRock.setOnClickListener { onClick(ibRock) }
        ibPaper.setOnClickListener { onClick(ibPaper) }
        ibScissors.setOnClickListener { onClick(ibScissors) }
    }

    private fun onClick(button: ImageButton) {
        var ivYou = findViewById<ImageView>(R.id.ivPlayer)
        when (button) {
            ibRock -> ivYou.setImageResource(R.drawable.rock)
            ibPaper -> ivYou.setImageResource(R.drawable.paper)
            ibScissors -> ivYou.setImageResource(R.drawable.scissors)
        }
        ivComputer.setImageResource(IMAGES_LIST[random.nextInt(IMAGES_LIST.size)])
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
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
