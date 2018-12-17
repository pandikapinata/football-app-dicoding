package com.example.pandu.finalproject

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.pandu.finalproject.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testFootballAppBehaviour() {

        Thread.sleep(3000)
        //Last Match Fragment
        Espresso.onView(ViewMatchers.withId(rv_last_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(rv_last_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
        Espresso.onView(ViewMatchers.withId(rv_last_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))

        // Date
        Espresso.onView(ViewMatchers.withId(tv_match_date_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Home Team
        Espresso.onView(ViewMatchers.withId(iv_home_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_home_name_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_score_home_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Away Team
        Espresso.onView(ViewMatchers.withId(iv_away_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_away_name_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_score_away_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Click Favorite
        Espresso.onView(ViewMatchers.withId(add_to_fav)).perform(click())
        pressBack()

        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(vp_match)).perform(swipeLeft())

        Thread.sleep(3000)
        //Next Match Fragment
        Espresso.onView(ViewMatchers.withId(rv_next_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(rv_next_match))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(rv_next_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))

        // Date
        Espresso.onView(ViewMatchers.withId(tv_match_date_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Home Team
        Espresso.onView(ViewMatchers.withId(iv_home_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_home_name_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Away Team
        Espresso.onView(ViewMatchers.withId(iv_away_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(tv_away_name_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        //Click Favorite
        Espresso.onView(ViewMatchers.withId(add_to_fav)).perform(click())

        pressBack()
    }
}