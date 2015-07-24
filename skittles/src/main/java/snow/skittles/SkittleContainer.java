package snow.skittles;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by aashrai on 3/7/15.
 */
@DefaultBehavior(SkittleContainer.FloatingLayoutBehavior.class)
public class SkittleContainer extends LinearLayout {


    public SkittleContainer(Context context) {
        super(context);
    }

    public SkittleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkittleContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @SuppressWarnings("unused")
    public static class FloatingLayoutBehavior extends CoordinatorLayout.Behavior<SkittleContainer> {

        private static final boolean SNACKBAR_BEHAVIOR_ENABLED;
        private float mTranslationY;
        private boolean mIsAnimatingOut;

        public FloatingLayoutBehavior() {
        }

        public FloatingLayoutBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, SkittleContainer child, View dependency) {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, SkittleContainer child, View dependency) {
            if (dependency instanceof Snackbar.SnackbarLayout) {
                this.updateFabTranslationForSnackbar(parent, child, dependency);
            }
            return true;
        }

        private void updateFabTranslationForSnackbar(CoordinatorLayout parent, SkittleContainer container, View snackbar) {
            float translationY = this.getFabTranslationYForSnackbar(parent, container);
            if ((translationY == -snackbar.getHeight())) {
                ViewCompat.animate(container).translationY(-translationY).setInterpolator(new FastOutLinearInInterpolator()).setListener(null);
            } else if (translationY != this.mTranslationY) {
                ViewCompat.animate(container).cancel();
                if (Math.abs(translationY - this.mTranslationY) == (float) snackbar.getHeight()) {
                    ViewCompat.animate(container).translationY(translationY).setInterpolator(new FastOutLinearInInterpolator()).setListener(null);
                } else {
                    ViewCompat.setTranslationY(container, translationY);
                }

                this.mTranslationY = translationY;
            }

        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout parent, SkittleContainer container) {
            float minOffset = 0.0F;
            List dependencies = parent.getDependencies(container);
            int i = 0;

            for (int z = dependencies.size(); i < z; ++i) {
                View view = (View) dependencies.get(i);
                if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(container, view)) {
                    minOffset = Math.min(minOffset, ViewCompat.getTranslationY(view) - (float) view.getHeight());
                }
            }

            return minOffset;
        }

        static {

            SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;
        }
    }
}
