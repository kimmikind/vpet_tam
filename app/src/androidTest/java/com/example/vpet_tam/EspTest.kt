import com.example.vpet_tam.view.MainActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions
import com.example.vpet_tam.view.ChooseFragment
import com.example.vpet_tam.R
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.action.ViewActions.scrollTo
import com.example.vpet_tam.view.StartFragment


@RunWith(AndroidJUnit4::class)
class ChooseFragmentTest {
    @Test
    fun testEditTextInputAndImageButtonSelection() {
        // Запускаем фрагмент ChooseFragment
        val scenario = launchFragmentInContainer<ChooseFragment>()

        // Проверяем, что EditText для ввода имени существует
        Espresso.onView(ViewMatchers.withId(R.id.type_name_txt))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Вводим текст в EditText
        val name = "TestPet"
        Espresso.onView(ViewMatchers.withId(R.id.type_name_txt))
            .perform(ViewActions.typeText(name), ViewActions.closeSoftKeyboard())

        // Проверяем, что текст введен корректно
        Espresso.onView(ViewMatchers.withId(R.id.type_name_txt))
            .check(ViewAssertions.matches(ViewMatchers.withText(name)))

        // Выбираем ImageButton с id iconBear
        Espresso.onView(ViewMatchers.withId(R.id.icon_bear))
            .perform(ViewActions.click())

        // Проверяем, что ImageButton с id iconBear был выбран
        Espresso.onView(ViewMatchers.withId(R.id.icon_bear))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }
}

