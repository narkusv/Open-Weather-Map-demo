package lt.vn.openweathermapcleanmvvm.weather

import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lt.vn.openweathermapcleanmvvm.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(WeatherActivity::class.java, false, false)

    @Test
    fun listGoesOverTheFold() {
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.etCity)).check(matches(isDisplayed()))
        onView(withId(R.id.ibContinue)).check(matches(isDisplayed()))
    }

    @Test
    fun errorMessageIsDisplayedWhenCityNameIsNotValid() {
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.etCity)).perform(typeText(""))
        onView(withId(R.id.ibContinue)).perform(click())

        checkSnackBarText(R.string.error_city_name_invalid)
    }

    @Test
    fun detailFragmentIsDisplayedWhenContinueButtonIsPressed() {
        val mockNavController = mockk<NavController>()
        every { mockNavController.navigate(any<Int>(), any()) } answers { nothing }

        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme).apply {
            onFragment { fragment ->
                Navigation.setViewNavController(fragment.requireView(), mockNavController)
            }
        }

        onView(withId(R.id.etCity)).perform(typeText("Vilnius"))
        onView(withId(R.id.ibContinue)).perform(click())

        verify { mockNavController.navigate(R.id.action_homeFragment_to_detailFragment, any()) }
        confirmVerified(mockNavController)
    }

    @Test
    fun historyFragmentIsDisplayedWhenHistoryButtonIsPressed() {
        val mockNavController = mockk<NavController>()
        every { mockNavController.navigate(any<Int>()) } answers { nothing }

        launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme).apply {
            onFragment { fragment ->
                Navigation.setViewNavController(fragment.requireView(), mockNavController)
            }
        }

        onView(withId(R.id.bHistory)).perform(click())

        verify {
            mockNavController.navigate(
                R.id.action_homeFragment_to_historyFragment
            )
        }
        confirmVerified(mockNavController)
    }

    private fun checkSnackBarText(@StringRes resource: Int) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(resource)))
    }
}


