package snow.skittles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Add this as the root view of your layouts
 */
@SuppressWarnings("ALL")
public class SkittleLayout extends CoordinatorLayout implements View.OnClickListener, Animator.AnimatorListener {

    LinearLayout skittleContainer;
    FloatingActionButton skittleMain;
    Boolean animatable;
    int flag = 0, color;
    List<Float> yList = new ArrayList<Float>();

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
        if (getChildCount() != 1)
            addSkittleOnTop();

    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        if (getChildCount() != 1)
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

        removeView(findViewById(R.id.skittle_container));

        skittleContainer = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addViewInLayout(skittleContainer, -1, skittleContainer.getLayoutParams());
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);

        setMainSkittleColor(color);
        skittleMain.setImageDrawable(drawable);
        skittleMain.setOnClickListener(this);

    }

    private void init(AttributeSet attrs) {

        //Add the main FloatingActionButton by default
        skittleContainer = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addView(skittleContainer);
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);
        skittleMain.setOnClickListener(this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SkittleLayout);

        try {
            color = array.getResourceId(R.styleable.SkittleLayout_mainSkittleColor, 0);
            setMainSkittleColor(color);
            Drawable drawable = array.getDrawable(R.styleable.SkittleLayout_mainSkittleIcon);
            skittleMain.setImageDrawable(drawable);
        } finally {
            array.recycle();
        }

    }


    public void setMainSkittleColor(@ColorRes int color) {
        skittleMain.setBackgroundTintList(Utils.getColorStateList(color, getContext()));
    }

    public void setMainSkittleAnimatable(boolean animatable) {
        this.animatable = animatable;
    }

    public boolean getMainSkittleAnimatable() {
        return animatable;
    }


    public void addFab(View fab) {
        //Add all button for at the same index for laying out the skittles vertically

        if (fab.getTag().equals("miniSkittle"))
            fab.setTag("Skittle " + skittleContainer.getChildCount());
        skittleContainer.addView(fab, 0);
    }

    public LinearLayout getSkittleContainer() {
        return skittleContainer;
    }

    @Override
    public void onClick(View v) {
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

    private void toggleMainSkittle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(skittleMain
                , "rotation", 0f, 45f).setDuration(200);

        if (flag == 1)
            animator.setFloatValues(45f, 0f);
        animator.start();
    }


    private void toggleSkittles(View child, int index) {

        int duration = 200;
        FastOutLinearInInterpolator interpolator = new FastOutLinearInInterpolator();

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child,
                PropertyValuesHolder.ofFloat("Y", child.getY() + child.getMeasuredHeight() / 2, child.getY()),
                PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(duration);
        animator.setInterpolator(interpolator);

        if (flag == 0) {
            animator.setStartDelay((skittleContainer.getChildCount() - index) * 15);
            Log.d("Skittle Layout", "Animation");
            animator.start();
        } else {
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
            ((TextSkittleContainer) v).getSkittle().setClickability(clickability);
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
                child.setVisibility(GONE);
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
