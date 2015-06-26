package snow.skittles;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by aashrai on 17/6/15.
 */
public class SkittleLayout extends FrameLayout implements View.OnClickListener {

    LinearLayout skittleContainer;
    ObjectAnimator animator;

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
        ObjectAnimator animator;
        FastOutLinearInInterpolator interpolator=new FastOutLinearInInterpolator();

        for (int i = 0; i < skittleContainer.getChildCount(); i++) {
            child = skittleContainer.getChildAt(i);
            if (child.getId() != R.id.skittle_main) {
                child.setVisibility(View.VISIBLE);
                animator=ObjectAnimator.ofPropertyValuesHolder(child, PropertyValuesHolder.ofFloat("Y", child.getY() + 50
                        , child.getY()), PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(300);
                animator.setInterpolator(interpolator);
                animator.start();
            }
        }
    }
}
