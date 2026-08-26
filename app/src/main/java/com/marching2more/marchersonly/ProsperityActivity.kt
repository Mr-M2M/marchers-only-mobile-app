// Donavan McFadden
// SDC340 Mobile App Development - Final Project: Marchers Only
// ProsperityActivity = second activity. Builds the 5-step "Prosperity Path"
// list in code by inflating item_step.xml once per step.

package com.marching2more.marchersonly

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.marching2more.marchersonly.databinding.ActivityProsperityBinding
import com.marching2more.marchersonly.databinding.ItemStepBinding

class ProsperityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProsperityBinding

    /** Step data: title string res, description string res */
    private val steps = listOf(
        R.string.step1_title to R.string.step1_desc,
        R.string.step2_title to R.string.step2_desc,
        R.string.step3_title to R.string.step3_desc,
        R.string.step4_title to R.string.step4_desc,
        R.string.step5_title to R.string.step5_desc,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProsperityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.prosperity_title)
            setIcon(R.mipmap.ic_launcher)
        }

        steps.forEachIndexed { index, (titleRes, descRes) ->
            val stepBinding = ItemStepBinding.inflate(LayoutInflater.from(this), binding.stepContainer, false)
            stepBinding.textStepNumber.text = (index + 1).toString()
            stepBinding.textStepTitle.text = getString(titleRes)
            stepBinding.textStepDesc.text = getString(descRes)
            binding.stepContainer.addView(stepBinding.root)
        }

        NavHelper.setup(this, binding.bottomNav, R.id.nav_prosperity)
    }
}
