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

        // Additional module entry point, preserving the existing navigation and screens.
        val mortgageButton = android.widget.Button(this).apply {
            text = "MORECLOSE | Compare Mortgage Offers (Demo)"
            setOnClickListener { startActivity(Intent(this@HomeActivity, MortgageMarketplaceActivity::class.java)) }
        }
        val container = (binding.root.getChildAt(0) as android.widget.ScrollView)
            .getChildAt(0) as android.widget.LinearLayout
        container.addView(mortgageButton)
        val transactionButton = android.widget.Button(this).apply {
            text = "MORECLOSE | Transaction Coordination (Demo)"
            setOnClickListener { startActivity(Intent(this@HomeActivity, TransactionCoordinatorActivity::class.java)) }
        }
        container.addView(transactionButton)
        NavHelper.setup(this, binding.bottomNav, R.id.nav_home)
    }
}
