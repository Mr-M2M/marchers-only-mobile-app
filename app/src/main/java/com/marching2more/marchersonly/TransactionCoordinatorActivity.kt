package com.marching2more.marchersonly

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

/** Local-only demonstration transaction checklist. No third-party integrations or PII. */
class TransactionCoordinatorActivity : AppCompatActivity() {
    private val green = Color.rgb(11,40,24)
    private val gold = Color.rgb(212,175,55)
    private val steps = listOf(
        "Contract ratified",
        "Earnest money confirmed",
        "Loan application / Loan Estimate reviewed",
        "Inspection contingency addressed",
        "Appraisal reviewed",
        "Title search and exceptions reviewed by title professional",
        "Payoffs, liens and closing figures reconciled",
        "Closing Disclosure received and timing verified",
        "Closing documents signed and funding verified",
        "Deed accepted by county clerk / recording confirmed"
    )
    private fun label(text: String, big: Boolean = false): TextView = TextView(this).apply {
        this.text = text
        textSize = if (big) 21f else 14f
        setTextColor(green)
        setPadding(0,8,0,8)
        if (big) setTypeface(null,Typeface.BOLD)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply { title = "MORECLOSE Transaction"; setDisplayHomeAsUpEnabled(true) }
        val prefs = getSharedPreferences("moreclose_transaction_demo", MODE_PRIVATE)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(30,24,30,30)
            setBackgroundColor(Color.rgb(245,241,230))
        }
        setContentView(ScrollView(this).apply { addView(root) })
        root.addView(label("Transaction Coordination",true))
        root.addView(label("Private on-device demo checklist. No lender, title, escrow, or courthouse connection. Do not enter personal information."))
        root.addView(label("Property label (non-sensitive demo identifier)"))
        val property = EditText(this).apply {
            hint = "Example: Transaction 001"
            setSingleLine(true)
            setText(prefs.getString("property",""))
        }
        root.addView(property)
        root.addView(label("Target closing date (YYYY-MM-DD)"))
        val date = EditText(this).apply {
            hint = "2026-11-20"
            setSingleLine(true)
            setText(prefs.getString("date",""))
        }
        root.addView(date)
        root.addView(label("Assigned coordinator (role or alias only)"))
        val coordinator = EditText(this).apply {
            hint = "Example: Closing coordinator"
            setSingleLine(true)
            setText(prefs.getString("coordinator",""))
        }
        root.addView(coordinator)
        val progress = label("")
        root.addView(progress)
        val checks = steps.mapIndexed { index, step ->
            CheckBox(this).apply {
                text = step
                setTextColor(green)
                isChecked = prefs.getBoolean("step_$index",false)
                root.addView(this)
            }
        }
        fun updateProgress() {
            progress.text = "${checks.count { it.isChecked }} of ${checks.size} milestones complete"
        }
        checks.forEach { it.setOnCheckedChangeListener { _, _ -> updateProgress() } }
        updateProgress()
        val save = Button(this).apply {
            text = "Save local transaction progress"
            setBackgroundColor(gold)
            setTextColor(green)
            setOnClickListener {
                val d = date.text.toString().trim()
                if (d.isNotBlank() && !Regex("\\d{4}-\\d{2}-\\d{2}").matches(d)) {
                    date.error = "Use YYYY-MM-DD"
                    return@setOnClickListener
                }
                val edit = prefs.edit()
                    .putString("property",property.text.toString().take(100))
                    .putString("date",d)
                    .putString("coordinator",coordinator.text.toString().take(80))
                checks.forEachIndexed { i, check -> edit.putBoolean("step_$i",check.isChecked) }
                edit.apply()
                android.widget.Toast.makeText(this@TransactionCoordinatorActivity,
                    "Saved on this device only",android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        root.addView(save)
        root.addView(label("Title handoff checklist",true))
        listOf("Verify settlement agent and contact channel",
            "Request authorized title commitment and exception updates",
            "Track lien/payoff clearance and lender closing conditions",
            "Confirm escrow and signing through approved providers",
            "Obtain official recording reference and final policy").forEach {
            root.addView(label("• $it"))
        }
        root.addView(label("This screen does not establish legal title status, verify funds, meet disclosure obligations, or submit records. Actual transactions require secure authenticated multi-party workflows and qualified professional sign-off."))
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
