package snow.skittles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Utility class for managing the addition of skittles and click events
 */
@SuppressWarnings("ALL")
public class SkittleBuilder {

    private SkittleLayout mSkittleLayout;
    private TextSkittle textSkittle;
    private Context context;
    private int skittleCount = 1;
    private int color;

    private SkittleClickListener mListener;

    /**
     * Interface for click events
     */
    public interface SkittleClickListener {
        void onSkittleClick(Skittle skittle);

        void onTextSkittleClick(TextSkittle textSkittle);
    }

    private SkittleBuilder(Builder builder) {
        setUp(builder.mContext, builder.mSkittleLayout, builder.animatable, builder.miniSkittleColor);
        mSkittleLayout.setMainSkittleColor(builder.mainSkittleColor);
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
        skittle.setBackgroundTintList(Utils.getColorStateList(color, context));

        skittle.setOnClickListener(mSkittleClickListener);
        skittle.setPosition(skittleCount++);

        return skittle;
    }

    public TextSkittleBuilder makeTextSkittle(String text, @DrawableRes int icon) {
        return new TextSkittleBuilder(text, icon);
    }


    private void addTextSkittle(TextSkittle textSkittle) {

        textSkittle.setAlpha(0f);
        textSkittle.setPosition(skittleCount++);
        mSkittleLayout.addFab(textSkittle);
        textSkittle.getSkittle().setOnClickListener(mTextSkittleClickListener);
    }

    View.OnClickListener mSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Skittle Click");
            if (v.isClickable() && getmListener() != null)
                getmListener().onSkittleClick((Skittle) v);
        }
    };

    View.OnClickListener mTextSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Text Skittle Click");
            if (v.isClickable() && getmListener() != null)
                getmListener().onTextSkittleClick((TextSkittle) v.getParent());
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

    /**
     * Call this method to manually toggle the skittle menu
     */
    public void toggleSkittleMenu() {
        mSkittleLayout.animateMiniSkittles();
    }

    /**
     * Convenient builder pattern for instantiating the SkittleBuilder
     */
    public static class Builder {

        private final SkittleLayout mSkittleLayout;
        private final Context mContext;
        private boolean animatable;
        private int mainSkittleColor, miniSkittleColor;
        private Drawable mainSkittleIcon;

        public Builder(Context mContext, SkittleLayout mSkittleLayout) {
            this.mContext = mContext;
            this.mSkittleLayout = mSkittleLayout;
        }

        public Builder animatable(boolean animatable) {
            this.animatable = animatable;
            return this;
        }

        public Builder mainSkittleColor(int color) {
            this.mainSkittleColor = color;
            return this;
        }

        public Builder miniSkittleColor(int color) {
            this.miniSkittleColor = color;
            return this;
        }

        public Builder mainSkittleIcon(@DrawableRes int iconRes) {
            this.mainSkittleIcon = mContext.getResources().getDrawable(iconRes);
            return this;
        }

        public SkittleBuilder build() {
            return new SkittleBuilder(this);
        }
    }

    /**
     * Convenient builder pattern for creating @link snow.skittles.TextSkittle
     */
    public class TextSkittleBuilder {
        private final TextView textView;
        private final FloatingActionButton miniSkittle;
        private final TextSkittle textSkittle;

        public TextSkittleBuilder(String text, @DrawableRes int icon) {
            textSkittle = (TextSkittle) LayoutInflater.from(context)
                    .inflate(R.layout.text_action_skittle, mSkittleLayout.getSkittleContainer(), false);
            textView = textSkittle.getTextView();
            textView.setText(text);
            miniSkittle = textSkittle.getSkittle();
            miniSkittle.setImageDrawable(context.getResources().getDrawable(icon));
        }

        public TextSkittleBuilder setTextColor(@ColorRes int textColor) {
            textView.setTextColor(context.getResources().getColor(textColor));
            return this;
        }

        public TextSkittleBuilder setTextSize(@DimenRes int size) {
            textView.setTextSize(context.getResources().getDimensionPixelSize(size));
            return this;
        }

        public TextSkittleBuilder setTextBackgroundColor(@ColorRes int backgroundColor) {
            textView.setBackgroundColor(context.getResources().getColor(backgroundColor));
            return this;
        }

        public TextSkittleBuilder setTextBackground(@DrawableRes int drawable) {
            textView.setBackground(context.getResources().getDrawable(drawable));
            return this;
        }

        public TextSkittleBuilder setSkittleColor(int color) {
            miniSkittle.setBackgroundTintList(Utils.getColorStateList(color, context));
            return this;
        }

        public TextSkittleBuilder setSkittleIcon(@DrawableRes int drawable) {
            miniSkittle.setImageDrawable(context.getResources().getDrawable(drawable));
            return this;
        }

        public void add() {
            addTextSkittle(textSkittle);
        }
    }
}
