package com.example.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class CalculatorTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private  fun enterCost(cost:String) {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText(cost))
            .perform(ViewActions.closeSoftKeyboard())
    }

    private fun uiClick(button:Int) {
        onView(withId(button))
            .perform(click())
    }

    private fun getResult(result:String) {
        uiClick(R.id.calculate_button)
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString(result))))
    }

    private fun calculateTip(cost:String, result:String, actions:()->Unit = {}) {
        enterCost(cost)
        actions()
        getResult(result)
    }


    @Test
    fun calculate20PercentTip() {
        calculateTip("50.00", "10,00")
    }

    @Test
    fun calculate18PercentTip() {
        calculateTip("50.00", "9,00") { uiClick(R.id.option_eighteen_percent) }

    }

    @Test
    fun calculate15PercentTip() {
        calculateTip("50.00", "8,00") { uiClick(R.id.option_fifteen_percent) }
    }

    @Test
    fun noRoundUp20(){
        uiClick(R.id.round_up_switch)
        calculateTip("41.00", "8,20")
    }

   @Test
   fun noRoundUp18() {
       uiClick(R.id.round_up_switch)
       calculateTip("41.00", "7,38") { uiClick(R.id.option_eighteen_percent) }
   }

   @Test
   fun noRoundUp15() {
       uiClick(R.id.round_up_switch)
       calculateTip("41.00", "6,15") { uiClick(R.id.option_fifteen_percent) }
   }


}