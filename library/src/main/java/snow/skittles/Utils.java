package snow.skittles;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;

/**
 * Created by aashrai on 29/6/15.
 */
public class Utils {

    public static ColorStateList getColorStateList(@ColorRes int color, Context context) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled},
                new int[]{android.R.attr.state_focused, android.R.attr.state_enabled},
                new int[]{}
        };
        int[] colors = new int[]{
                context.getResources().getColor(color),
                context.getResources().getColor(color),
                context.getResources().getColor(color)
        };
        return new ColorStateList(states, colors);
    }
}
