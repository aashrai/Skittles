package snow.skittles;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;

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
    public static ColorStateList getColorStateList(int color, Context context) {

        int parsedColor;

        try {
            parsedColor = context.getResources().getColor(color);
        } catch (Resources.NotFoundException e) {
            parsedColor = color;
        }
        int[][] states = new int[][]{
                new int[]{}
        };
        int[] colors = new int[]{
                parsedColor
        };
        return new ColorStateList(states, colors);
    }

    static int fetchAccentColor(Context mContext) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = mContext.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }


}
