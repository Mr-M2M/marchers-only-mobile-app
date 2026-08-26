// Donavan McFadden
// SDC340 Mobile App Development - Final Project: Marchers Only
// Small shared helper so each activity wires up the same 3-tab bottom
// navigation bar without repeating the same when/if block three times.

package com.marching2more.marchersonly

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavHelper {

    /**
     * Wires [bottomNav] so tapping a tab starts the matching activity.
     * [currentId] is pre-selected and its tap is a no-op (already there).
     */
    fun setup(activity: Activity, bottomNav: BottomNavigationView, currentId: Int) {
        bottomNav.selectedItemId = currentId

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == currentId) {
                true
            } else {
                val target: Class<*> = when (item.itemId) {
                    R.id.nav_home -> HomeActivity::class.java
                    R.id.nav_prosperity -> ProsperityActivity::class.java
                    else -> AgentContactActivity::class.java
                }
                activity.startActivity(Intent(activity, target))
                activity.overridePendingTransition(0, 0)
                activity.finish()
                true
            }
        }
    }
}
