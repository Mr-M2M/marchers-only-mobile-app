// Donavan McFadden
// SDC340 Mobile App Development - Final Project: Marchers Only
// HomeActivity = launcher activity. Shows the brand hero and two feature
// cards that route into the other two activities.

package com.marching2more.marchersonly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marching2more.marchersonly.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.home_title)
            setIcon(R.mipmap.ic_launcher)
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
        }

        binding.cardProsperity.setOnClickListener {
            startActivity(Intent(this, ProsperityActivity::class.java))
        }
        binding.cardAgent.setOnClickListener {
            startActivity(Intent(this, AgentContactActivity::class.java))
        }

        NavHelper.setup(this, binding.bottomNav, R.id.nav_home)
    }
}
