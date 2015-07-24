package snow.skittlessample;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import snow.skittles.Skittle;
import snow.skittles.Utils;


/**
 * Created by aashrai on 5/7/15.
 */
public class TestUtils {

    static Matcher<View> hasSkittleColor(final int color) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

                description.appendText("with skittle color: ");
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            protected boolean matchesSafely(View skittle) {
                ColorStateList colorStateList = Utils.getColorStateList(color, InstrumentationRegistry.getTargetContext());
                return colorStateList.getDefaultColor() == ((Skittle) skittle).getBackgroundTintList().getDefaultColor();
            }
        };
    }

    static Matcher<View> hasSkittleDrawable(final int id) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                Drawable drawable = InstrumentationRegistry.getTargetContext().getResources().getDrawable(id);
                return ((FloatingActionButton) item).getDrawable().getConstantState().equals(drawable.getConstantState());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with skittle drawable: ");
            }
        };
    }
}
