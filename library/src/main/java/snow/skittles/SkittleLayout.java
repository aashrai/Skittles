package snow.skittles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Add this as the root view of your layouts
 */
public class SkittleLayout extends FrameLayout implements View.OnClickListener, Animator.AnimatorListener {

    LinearLayout skittleContainer;
    FloatingActionButton skittleMain;
    int flag = 0;
    List<Float> yList = new ArrayList<Float>();

    public SkittleLayout(Context context) {
        super(context);
    }

    public SkittleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SkittleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        addSkittleOnTop();

    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        addSkittleOnTop();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        addSkittleOnTop();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        addSkittleOnTop();
    }


    private void addSkittleOnTop() {

        skittleContainer = (LinearLayout) findViewById(R.id.skittle_container);
        removeView(skittleContainer);

        skittleContainer = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addViewInLayout(skittleContainer, -1, skittleContainer.getLayoutParams());
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);
        skittleMain.setOnClickListener(this);

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }

    private void init() {

        //Add the main FloatingActionButton by default
        skittleContainer = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addView(skittleContainer);
        skittleMain = (FloatingActionButton) skittleContainer.findViewById(R.id.skittle_main);
        skittleMain.setOnClickListener(this);

    }


    public void addFab(View fab) {

        //Add all button for at the same index for laying out the skittles vertically
        skittleContainer.addView(fab, 0);
    }

    public LinearLayout getSkittleContainer() {
        return skittleContainer;
    }

    @Override
    public void onClick(View v) {
        View child;
        toggleMainSkittle();
        int COUNT = skittleContainer.getChildCount();

        if (flag == 0) {
            for (int i = 0; i < COUNT; i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
                    if (yList.size() != COUNT - 1)
                        yList.add(child.getY());
                    toggleSkittles(child, i);
                }
            }
            flag = 1;
        } else if (flag == 1) {
            for (int i = 0; i < COUNT; i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
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
            Log.d("Skittle Layout","Animation");
            animator.start();
        } else {
            animator.setStartDelay(index * 15);
            animator.addListener(this);
            animator.reverse();
        }
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
