package snow.skittlessample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by aashrai on 2/7/15.
 */
@SuppressWarnings("ALL")
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextSkittlesTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void textSkittlesTest_sameActivity(){

        Object object = "miniSkittle";
        onView(allOf(withTagValue(equalTo(object)), hasSibling(withText(R.string.house_lannister))))
                .check(matches(not(isClickable())));

        onView(withId(R.id.skittle_main))
                .check(matches(isClickable())).perform(click());
        onView(withChild(withText(R.string.house_lannister))).check(matches(isDisplayed()));
        onView(allOf(withTagValue(equalTo(object)), hasSibling(withText(R.string.house_lannister))))
                .check(matches(isClickable()))
                .perform(click())
                .check(isAbove(withChild(withId(R.id.snackbar_text))));

        onView(withId(R.id.skittle_main)).perform(click());
        onView(withChild(withText(R.string.house_lannister))).check(matches(not(isDisplayed())));
        onView(allOf(withTagValue(equalTo(object)), hasSibling(withText(R.string.house_lannister))))
                .check(matches(not(isClickable())));

    }


}
