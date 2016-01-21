package snow.skittles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Add this as the root view of your layouts
 */
@SuppressWarnings("ALL")
public class SkittleLayout extends CoordinatorLayout implements View.OnClickListener, Animator.AnimatorListener {

    private static final float FADE_IN_ANIMATION_DEFAULT_END_VALUE = 0.6f;
    private static final int FADE_IN_ANIMATION_DEFAULT_DURATION = 250;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SKITTLE_NONE_BACKGROUND_MODE, SKITTLE_STANDARD_BACKGROUND_MODE})
    public @interface SkittleBackgroundMode {}
    private static final int SKITTLE_NONE_BACKGROUND_MODE = 0;
    private static final int SKITTLE_STANDARD_BACKGROUND_MODE = 1;

    private SkittleContainer skittleContainer;
    private FloatingActionButton skittleMain;
    private static boolean animatable;
    private static int flag = 0, color;
    private static final List<Float> yList = new ArrayList<Float>();
    private final static LinearInterpolator inInterpolator = new LinearInterpolator();
    private final static OvershootInterpolator outInterpolator = new OvershootInterpolator(5);

    private View skittleWindowBackgroundContainer;

    private int skittleBackgroundMode;
    private int skittleBackgroundAnimationDuration;
    private float skittleBackgroundAlpha;
    private boolean skittleBackgroundAnimation;

    public SkittleLayout(Context context) {
        super(context);
    }

    public SkittleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SkittleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout))
            addSkittleOnTop();

    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout))
            addSkittleOnTop();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        if (getChildCount() != 1)
            addSkittleOnTop();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        if (getChildCount() != 1)
            addSkittleOnTop();
    }


    /**
     * Utility method called after addView to ensure the skittle container is on top
     */
    private void addSkittleOnTop() {

        Drawable drawable = skittleMain.getDrawable();

        removeView(skittleContainer);
        addViewInLayout(skittleContainer, -1, skittleContainer.getLayoutParams());
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);

        setMainSkittleColor();
        if (drawable != null)
            skittleMain.setImageDrawable(drawable);
        skittleMain.setOnClickListener(this);

    }

    private void init(AttributeSet attrs) {

        //Add the main FloatingActionButton by default
        skittleContainer = (SkittleContainer) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addView(skittleContainer);
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);
        skittleMain.setOnClickListener(this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SkittleLayout);

        try {
            color = array.getResourceId(R.styleable.SkittleLayout_mainSkittleColor
                    , Utils.fetchAccentColor(getContext()));

            setSkittleWindowBackgroundParams(array);
            setMainSkittleColor();
            Drawable drawable = array.getDrawable(R.styleable.SkittleLayout_mainSkittleIcon);
            if (drawable != null)
                skittleMain.setImageDrawable(drawable);
        } finally {
            array.recycle();
        }

    }

    private void setSkittleWindowBackgroundParams(TypedArray array) {
        skittleBackgroundMode = array.getInt(R.styleable.SkittleLayout_skittleMenuBackgroundMode,
                SKITTLE_NONE_BACKGROUND_MODE);

        switch (skittleBackgroundMode) {
            case SKITTLE_STANDARD_BACKGROUND_MODE:
                skittleWindowBackgroundContainer = LayoutInflater.from(getContext())
                        .inflate(R.layout.view_menu_window_background, this, false);

                skittleWindowBackgroundContainer.setBackgroundColor(array.getColor(R.styleable.SkittleLayout_skittleStandardBackgroundColor,
                                Utils.fetchAccentColor(getContext())));
                skittleWindowBackgroundContainer.setAlpha(
                        array.getFloat(R.styleable.SkittleLayout_skittleStandardBackgroundColor, FADE_IN_ANIMATION_DEFAULT_END_VALUE));

                skittleBackgroundAnimationDuration = array.getInt(R.styleable.SkittleLayout_skittleBackgroundAnimationDuration,
                        FADE_IN_ANIMATION_DEFAULT_DURATION);
                skittleBackgroundAnimation = array.getBoolean(R.styleable.SkittleLayout_skittleBackgroundAnimation, false);
                skittleBackgroundAlpha = array.getFloat(R.styleable.SkittleLayout_skittleStandardBackgroundAlpha, FADE_IN_ANIMATION_DEFAULT_END_VALUE);
                break;
            case SKITTLE_NONE_BACKGROUND_MODE:
            default:
                break;
        }

    }

    private void setMainSkittleColor() {
        skittleMain.setBackgroundTintList(Utils.getColorStateList(color, getContext()));
    }

    void setMainSkittleColor(int color) {
        this.color = color;
        skittleMain.setBackgroundTintList(Utils.getColorStateList(color, getContext()));
    }

    void setMainSkittleAnimatable(boolean animatable) {
        this.animatable = animatable;
    }

    boolean getMainSkittleAnimatable() {
        return animatable;
    }


    void addFab(View fab) {
        //Add all button for at the same index for laying out the skittles vertically

        if (fab.getTag() != null && fab.getTag().equals("miniSkittle"))
            fab.setTag("Skittle " + skittleContainer.getChildCount());
        skittleContainer.addView(fab, 0);
    }

    public SkittleContainer getSkittleContainer() {
        return skittleContainer;
    }

    @Override
    public void onClick(View v) {
        animateMiniSkittles();
    }

    public void animateMiniSkittles() {
        animateSkittleBackground();

        View child;
        int COUNT = skittleContainer.getChildCount();
        if (getMainSkittleAnimatable())
            toggleMainSkittle();

        if (flag == 0) {
            for (int i = 0; i < COUNT; i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
                    if (yList.size() != COUNT - 1)
                        yList.add(child.getY());

                    child.setVisibility(VISIBLE);
                    toggleSkittleClick(child, true);
                    toggleSkittles(child, i);
                }
            }
            flag = 1;
        } else if (flag == 1) {
            for (int i = 0; i < COUNT; i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
                    child.setVisibility(VISIBLE);
                    toggleSkittleClick(child, true);
                    toggleSkittles(child, i);
                }
            }
            flag = 0;
        }

    }

    private void animateSkittleBackground() {
        if (skittleBackgroundMode == SKITTLE_NONE_BACKGROUND_MODE) {
            return;
        }

        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(skittleWindowBackgroundContainer, "alpha", 0, skittleBackgroundAlpha);
        fadeInAnimator.setInterpolator(new LinearInterpolator());
        fadeInAnimator.setDuration(skittleBackgroundAnimationDuration);

        switch (flag) {
            case 0:
                addView(skittleWindowBackgroundContainer);
                if (skittleBackgroundAnimation) {
                    fadeInAnimator.start();
                } else {
                    skittleWindowBackgroundContainer.setAlpha(skittleBackgroundAlpha);
                }
                break;
            case 1:
                if (skittleBackgroundAnimation) {
                    fadeInAnimator.reverse();
                }
                removeView(skittleWindowBackgroundContainer);
                break;
        }
    }

    private void toggleMainSkittle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(skittleMain
                , "rotation", 0f, 45f).setDuration(200);

        if (flag == 1)
            animator.setFloatValues(45f, 0f);
        animator.start();
    }


    private void toggleSkittles(View child, int index) {

        int duration = 200;


        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child,
                PropertyValuesHolder.ofFloat("Y", child.getY() + child.getMeasuredHeight() / 2, child.getY()),
                PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(duration);

        if (flag == 0) {
            animator.setInterpolator(outInterpolator);
            animator.setStartDelay((skittleContainer.getChildCount() - index) * 15);
            Log.d("Skittle Layout", "Animation");
            animator.start();
        } else {
            animator.setInterpolator(inInterpolator);
            animator.setStartDelay(index * 15);
            animator.addListener(this);
            animator.reverse();
        }
    }

    /**
     * Used to set wether the skittle should be clickable or not
     *
     * @param v
     * @param clickability
     */
    private void toggleSkittleClick(View v, boolean clickability) {

        if (v.getTag().equals(getResources().getString(R.string.text_skittle_tag)))
            ((TextSkittle) v).getSkittle().setClickability(clickability);
        else
            ((Skittle) v).setClickability(clickability);
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        View child;
        //To place views at initial coordinates
        for (int i = 0; i < skittleContainer.getChildCount(); i++) {
            child = skittleContainer.getChildAt(i);
            if (child.getId() != R.id.skittle_main) {
                child.setAlpha(0f);
                child.setY(yList.get(i));
                toggleSkittleClick(child, false);
                child.setVisibility(INVISIBLE);
            }
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
