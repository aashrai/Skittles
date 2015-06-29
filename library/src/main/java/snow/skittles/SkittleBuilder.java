package snow.skittles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
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

    //TODO add option for changing the color of the main skittle
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
     * @param color pass the color for the mini skittles
     */
    public SkittleBuilder(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout, boolean animatable
            , @ColorRes @Nullable int color) {
        this.mSkittleLayout = mSkittleLayout;
        this.context = context;
        mSkittleLayout.setMainSkittleAnimatable(animatable);
        this.color = color;
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
     * Call this to add a simple skittle
     *
     * @param icon
     */
    public void addSkittle(@NonNull Drawable icon) {

        Skittle skittle = (Skittle) LayoutInflater.from(context)
                .inflate(R.layout.action_skittle, mSkittleLayout.getSkittleContainer(), false);
        skittle.setImageDrawable(icon);
        skittle.setAlpha(0f);

        if (color != null) {
            skittle.setBackgroundTintList(Utils.getColorStateList(color, context));
        }

        skittle.setOnClickListener(mSkittleClickListener);
        skittle.setPosition(skittleCount++);
        mSkittleLayout.addFab(skittle);

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
     * @param drawable icon for the skittle
     * @param text     text displayed with the skittle
     * @return
     */
    public TextSkittle makeTextSkittle(@Nullable Drawable drawable, @NonNull String text) {

        textSkittle = new TextSkittle(context, mSkittleLayout.getSkittleContainer());
        textSkittle.setIcon(drawable);
        textSkittle.setText(text);
        textSkittle.setSkittleColor(color);

        return textSkittle;
    }

    View.OnClickListener mSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Skittle Click");
            getmListener().onSkittleClick((Skittle) v);
        }
    };

    View.OnClickListener mTextSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Text Skittle Click");
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
