package snow.skittles;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;

/**
 * Created by aashrai on 29/6/15.
 */
public class Utils {

    /**
     * Used internally,a hack for changing the color of FloatingActionButton
     *
     * @param color
     * @param context
     * @return
     */
    public static ColorStateList getColorStateList(@ColorRes int color, Context context) {
        int[][] states = new int[][]{
                new int[]{}
        };
        int[] colors = new int[]{
                context.getResources().getColor(color),
        };
        return new ColorStateList(states, colors);
    }
}
