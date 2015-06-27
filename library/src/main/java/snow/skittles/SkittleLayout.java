package snow.skittles;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashrai on 17/6/15.
 */
public class SkittleLayout extends FrameLayout implements View.OnClickListener, Animator.AnimatorListener {

    LinearLayout skittleContainer;
    ObjectAnimator animator;
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

    private void init() {

        skittleContainer = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.skittle_container, this, false);
        addView(skittleContainer);
        findViewById(R.id.skittle_main).setOnClickListener(this);

    }

    public void addFab(View fab) {

        skittleContainer.addView(fab, 0);
    }


    public LinearLayout getSkittleContainer() {
        return skittleContainer;
    }

    @Override
    public void onClick(View v) {
        View child;
        FastOutLinearInInterpolator interpolator = new FastOutLinearInInterpolator();
        Log.d("Skittle Layout", "" + skittleContainer.getChildCount());

        if (flag == 0) {
            flag = 1;
            yList.clear();
            for (int i = 0; i < skittleContainer.getChildCount(); i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
                    yList.add(child.getY());
                    animator = ObjectAnimator.ofPropertyValuesHolder(child,
                            PropertyValuesHolder.ofFloat("Y", child.getY() + child.getMeasuredHeight() / 2, child.getY()),
                            PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(200);
                    animator.setInterpolator(interpolator);
                    animator.start();
                }
            }
        } else if (flag == 1) {
            flag = 0;
            for (int i = 0; i < skittleContainer.getChildCount(); i++) {
                child = skittleContainer.getChildAt(i);
                if (child.getId() != R.id.skittle_main) {
                    animator = ObjectAnimator.ofPropertyValuesHolder(child,
                            PropertyValuesHolder.ofFloat("Y", child.getY() + child.getMeasuredHeight() / 2, child.getY()),
                            PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(200);
                    animator.setInterpolator(interpolator);
                    animator.addListener(this);
                    animator.reverse();
                }
            }
        }
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        View child;
        for (int i = 0; i < skittleContainer.getChildCount(); i++) {
            child = skittleContainer.getChildAt(i);
            if (child.getId() != R.id.skittle_main) {
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
