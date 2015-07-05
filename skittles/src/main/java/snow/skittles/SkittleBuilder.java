package snow.skittles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Utility class for managing the addition of skittles and click events
 */
@SuppressWarnings("ALL")
public class SkittleBuilder {

    SkittleLayout mSkittleLayout;
    TextSkittle textSkittle;
    Context context;
    int skittleCount = 1;
    Integer color;

    private SkittleClickListener mListener;

    /**
     * Interface for click events
     */
    public interface SkittleClickListener {
        void onSkittleClick(Skittle skittle);

        void onTextSkittleClick(TextSkittle textSkittle);
    }

    /**
     * @param context        pass in the context of the activity
     * @param mSkittleLayout pass an instance of the skittle you have set as the root of your layouts
     * @param animatable     should the main Skittle have an animation
     */
    public SkittleBuilder(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout, boolean animatable) {
        this.mSkittleLayout = mSkittleLayout;
        this.context = context;
        mSkittleLayout.setMainSkittleAnimatable(animatable);
    }

    /**
     * @param context        pass in the context of the activity
     * @param mSkittleLayout pass an instance of the skittle you have set as the root of your layouts
     * @param animatable     should the main Skittle have an animation
     * @param color          pass the color for all mini skittles,you can pass any either a reference or a generated color
     */
    public SkittleBuilder(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout, boolean animatable
            , @Nullable Integer color) {

        setUp(context, mSkittleLayout, animatable, color);

    }

    /**
     * @param context
     * @param mSkittleLayout
     * @param animatable
     * @param miniSkittleColor you can pass any either a reference or a generated color
     * @param mainSkittleColor pass the for the main Skittle,you can pass any either a reference or a generated color
     */
    public SkittleBuilder(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout, boolean animatable
            , @Nullable Integer miniSkittleColor, int mainSkittleColor) {

        setUp(context, mSkittleLayout, animatable, miniSkittleColor);
        mSkittleLayout.setMainSkittleColor(mainSkittleColor);
    }

    private void setUp(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout, boolean animatable
            , @Nullable Integer color) {
        this.mSkittleLayout = mSkittleLayout;
        this.context = context;
        this.color = color;
        mSkittleLayout.setMainSkittleAnimatable(animatable);

    }

    /**
     * Call this to change the icon of the main skitlle ie the normal sized FloatingActionButton
     *
     * @param icon
     */
    public void setMainSkittleIcon(@NonNull Drawable icon) {

        FloatingActionButton mSkittle = (FloatingActionButton) mSkittleLayout.findViewById(R.id.skittle_main);
        if (icon != null)
            mSkittle.setImageDrawable(icon);
    }

    /**
     * Call this to change the color of the main skittle,use this method only if you want to
     * change the color of the main skittle dynamically, else use the constructor @see #SkittleBuilder(@NonNull Context
     * , @NonNull SkittleLayout, boolean animatable, @Nullable Integer, int)
     *
     * @param color you can pass either a reference or a generated color,you can pass either
     *              a reference or a generated color
     */
    public void setMainSkittleColor(int color) {
        mSkittleLayout.setMainSkittleColor(color);
    }

    /**
     * Call this to add a simple skittle
     *
     * @param icon
     */
    public void addSkittle(@NonNull int icon) {


        mSkittleLayout.addFab(setUpMiniSkittle(icon));

    }

    public void addSkittle(@NonNull int icon, int color) {

        Skittle skittle = setUpMiniSkittle(icon);
        skittle.setBackgroundTintList(Utils.getColorStateList(color, context));
        mSkittleLayout.addFab(skittle);
    }

    private Skittle setUpMiniSkittle(int icon) {
        Skittle skittle = (Skittle) LayoutInflater.from(context)
                .inflate(R.layout.action_skittle, mSkittleLayout.getSkittleContainer(), false);
        skittle.setImageDrawable(context.getResources().getDrawable(icon));
        skittle.setAlpha(0f);
        skittle.setClickability(false);

        if (color != null) {
            skittle.setBackgroundTintList(Utils.getColorStateList(color, context));
        }

        skittle.setOnClickListener(mSkittleClickListener);
        skittle.setPosition(skittleCount++);

        return skittle;
    }


    /**
     * Call this method after @see #makeTextSkittle(Drawable,String) and modifying the look of
     * the skittle
     *
     * @param textSkittle
     */

    public void addTextSkittle(@NonNull TextSkittle textSkittle) {

        textSkittle.getTextSkittle().setAlpha(0f);
        textSkittle.setPosition(skittleCount++);
        mSkittleLayout.addFab(textSkittle.getTextSkittle());
        textSkittle.getSkittle().setOnClickListener(mTextSkittleClickListener);

    }

    /**
     * Call this method first while adding the text skittle
     * exposes rich methods for modifications,follow with a
     * call to @see #addTextSkittle(TextSkittle)
     *
     * @param icon
     * @param text
     * @return
     */
    public TextSkittle makeTextSkittle(@Nullable Integer icon, @NonNull String text) {

        textSkittle = new TextSkittle(context, mSkittleLayout.getSkittleContainer());
        textSkittle.setIcon(context.getResources().getDrawable(icon));
        textSkittle.setText(text);

        return textSkittle;
    }

    public TextSkittle makeTextSkittle(@Nullable Integer icon, @NonNull String text, int color) {


        textSkittle = makeTextSkittle(icon, text);
        textSkittle.setSkittleColor(color);

        return textSkittle;
    }


    View.OnClickListener mSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Skittle Click");
            Skittle skittle = (Skittle) v;
            if (v.isClickable())
                getmListener().onSkittleClick((Skittle) v);
        }
    };

    View.OnClickListener mTextSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Text Skittle Click");
            Skittle skittle = (Skittle) v;
            if (v.isClickable())
                getmListener().onTextSkittleClick(new TextSkittle((TextSkittleContainer) v.getParent()));
        }
    };

    public SkittleClickListener getmListener() {
        return mListener;
    }

    /**
     * Call this method for setting the click listener for the skittles
     *
     * @param mListener
     */
    public void setSkittleListener(@NonNull SkittleClickListener mListener) {
        this.mListener = mListener;
    }
}
