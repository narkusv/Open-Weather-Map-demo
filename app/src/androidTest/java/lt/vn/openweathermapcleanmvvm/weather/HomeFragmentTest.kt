package lt.vn.openweathermapcleanmvvm.weather

import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.di.appModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @Before
    fun setup() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(appModule)
            androidLogger()
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

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