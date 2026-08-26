// Donavan McFadden
// SDC340 Mobile App Development - Final Project: Marchers Only
// AgentContactActivity = third activity. Lets a client reach the agent
// through the device's own dialer, messaging, or email app.

package com.marching2more.marchersonly

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marching2more.marchersonly.databinding.ActivityAgentContactBinding

class AgentContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgentContactBinding

    // Placeholder contact details for the demo build.
    private val agentPhone = "7575550142"
    private val agentEmail = "donavan@marching2more.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.agent_title)
            setIcon(R.mipmap.ic_launcher)
        }

        binding.btnCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$agentPhone")))
        }
        binding.btnText.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$agentPhone")))
        }
        binding.btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$agentEmail")).apply {
                putExtra(Intent.EXTRA_SUBJECT, "Marchers Only - Let's Talk")
            }
            startActivity(intent)
        }

        NavHelper.setup(this, binding.bottomNav, R.id.nav_agent)
    }
}
