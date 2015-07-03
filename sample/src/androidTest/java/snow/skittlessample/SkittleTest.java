package snow.skittlessample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by aashrai on 3/7/15.
 */
@SuppressWarnings("ALL")
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SkittleTest {

    @Rule
    public ActivityTestRule<MainActivity2> mActivityRule = new ActivityTestRule<>(
            MainActivity2.class);

    @Test
    public void skittleTest_sameActivity() {

        Object object = "Skittle 1";
        onView(withId(R.id.skittle_main)).check(matches(isClickable()));

        onView(withTagValue(equalTo(object))).check(matches(not(isClickable())));
        onView(withId(R.id.skittle_main)).perform(click());

        onView(withTagValue(equalTo(object))).check(matches(isClickable()));
        onView(withId(R.id.skittle_main)).perform(click());

        onView(withTagValue(equalTo(object))).check(matches(not(isClickable())));
    }
}
