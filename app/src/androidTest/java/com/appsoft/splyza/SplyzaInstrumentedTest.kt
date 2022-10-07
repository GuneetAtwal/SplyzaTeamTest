package com.appsoft.splyza

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import com.appsoft.splyza.view.home.HomeActivity
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@LargeTest
class SplyzaInstrumentedTest {
    lateinit var activityScenarioRule: ActivityScenario<HomeActivity>

    @Before
    fun setup() {

    }

    @After
    fun close() {
        activityScenarioRule.close()
    }


    @Test
    fun home_fragment_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun invite_fragment_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(Matchers.anyOf(ViewMatchers.withText(R.string.back), ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.invite_members)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withText(R.string.coach)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.coach)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player_coach)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.supporter)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.btnCopyLink)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).perform(ViewActions.click())
    }

    @Test
    fun qr_fragment_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.share_qr_code)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).perform(ViewActions.click())
    }

    @Test
    fun invite_fragment_share_qr_code_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.btnShareQr)).perform(ViewActions.click())
    }

    @Test
    fun invite_fragment_copy_link_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnCopyLink)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun invite_fragment_change_permissions_test() {
        activityScenarioRule = ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withText(R.string.invite)).perform(ViewActions.click())
        Espresso.onView(Matchers.anyOf(ViewMatchers.withText(R.string.invite_members), ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.coach)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.coach)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player_coach)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(R.string.player)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.player)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}