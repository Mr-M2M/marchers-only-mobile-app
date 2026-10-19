package com.marching2more.marchersonly

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import kotlin.math.pow

/**
 * MORECLOSE marketplace demo. No network requests, credit checks, or actual lender offers.
 * Input stays in memory only; never collect SSNs, DOBs, or financial documents here.
 */
class MortgageMarketplaceActivity : AppCompatActivity() {
    private val green = Color.rgb(11, 40, 24)
    private val gold = Color.rgb(212, 175, 55)
    private val currency = NumberFormat.getCurrencyInstance()
    private data class Offer(val name: String, val rate: Double, val apr: Double,
                             val fees: Int, val satisfaction: Double, val reviews: Int, val days: Int)
    private val offers = listOf(
        Offer("Lender A · DEMO", 6.15, 6.34, 4200, 4.9, 241, 28),
        Offer("Lender B · DEMO", 6.00, 6.29, 6900, 4.5, 183, 35),
        Offer("Lender C · DEMO", 6.35, 6.45, 1850, 4.8, 397, 24)
    )
    private fun payment(principal: Double, rate: Double): Double {
        val monthly = rate / 1200.0
        return principal * monthly / (1 - (1 + monthly).pow(-360))
    }
    private fun line(content: String, size: Float = 15f, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = content
            textSize = size
            setTextColor(green)
            setPadding(0, 8, 0, 8)
            if (bold) setTypeface(null, Typeface.BOLD)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply { title = "MORECLOSE Mortgage"; setDisplayHomeAsUpEnabled(true) }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 28, 32, 32)
            setBackgroundColor(Color.rgb(245, 241, 230))
        }
        val scroll = ScrollView(this).apply { addView(root) }
        setContentView(scroll)
        root.addView(line("MORECLOSE | Marchers Only", 23f, true))
        root.addView(line("One intake. Multiple lenders. Transparent costs. Verified service.", 14f))
        root.addView(line("DEMO ONLY — no real applications or lender connections.", 12f, true))
        root.addView(line("Loan amount ($)", 15f, true))
        val amount = EditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setText("350000")
            contentDescription = "Mortgage loan amount"
        }
        root.addView(amount)
        val results = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val sortChoices = arrayOf("Lowest 5-year cost", "Lowest upfront fees", "Highest satisfaction", "Fastest closing")
        val spinner = android.widget.Spinner(this).apply {
            adapter = android.widget.ArrayAdapter(this@MortgageMarketplaceActivity,
                android.R.layout.simple_spinner_dropdown_item, sortChoices)
        }
        root.addView(line("Sort the offers", 15f, true))
        root.addView(spinner)
        val compare = Button(this).apply { text = "Compare sample lenders"; setBackgroundColor(gold); setTextColor(green) }
        root.addView(compare)
        root.addView(results)
        root.addView(line("Next phase: invite-only authentication, explicit borrower consent, verified lender submissions, official Loan Estimates, reviews tied to completed closings, and secure title handoff.", 13f))
        root.addView(line("Ratings, rates, APRs, fees, and closing times shown here are fictional. The 5-year estimate is only 60 principal-and-interest payments plus fees; it excludes remaining balance, taxes, insurance and other financing costs.", 12f))

        fun render() {
            results.removeAllViews()
            val principal = amount.text.toString().toDoubleOrNull()
            if (principal == null || principal <= 0 || principal > 10000000) {
                results.addView(line("Enter a valid loan amount between $1 and $10 million."))
                return
            }
            val ranked = when (spinner.selectedItemPosition) {
                1 -> offers.sortedBy { it.fees }
                2 -> offers.sortedByDescending { it.satisfaction }
                3 -> offers.sortedBy { it.days }
                else -> offers.sortedBy { payment(principal, it.rate) * 60 + it.fees }
            }
            ranked.forEachIndexed { index, offer ->
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(22, 16, 22, 16)
                    setBackgroundColor(Color.WHITE)
                    val margin = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    margin.topMargin = 18
                    layoutParams = margin
                }
                card.addView(line((if (index == 0) "★ Top for selected priority · " else "") + offer.name, 17f, true))
                card.addView(line("Rate ${offer.rate}%  •  APR ${offer.apr}%", 15f))
                card.addView(line("Monthly P&I: ${currency.format(payment(principal, offer.rate))}", 15f))
                card.addView(line("Lender fees: ${currency.format(offer.fees)}", 15f))
                card.addView(line("Illustrative 5-year payments + fees: ${currency.format(payment(principal, offer.rate) * 60 + offer.fees)}", 14f))
                card.addView(line("Demo happiness: ${offer.satisfaction}/5 (${offer.reviews} fictional reviews)", 14f))
                card.addView(line("Demo timeline: ${offer.days} days", 14f))
                results.addView(card)
            }
        }
        compare.setOnClickListener { render() }
        render()
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
