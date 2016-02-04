package snow.skittles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aashrai on 2/2/16.
 */
public class SkittleLayout extends CoordinatorLayout {

  SkittleContainer skittleContainer;

  public SkittleLayout(Context context) {
    super(context);
  }

  public SkittleLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SkittleLayout);
    int color;
    Drawable drawable;
    try {
      color = array.getResourceId(R.styleable.SkittleLayout_mainSkittleColor,
          fetchAccentColor(getContext()));
      drawable = array.getDrawable(R.styleable.SkittleLayout_mainSkittleIcon);
      if (drawable == null) {
        drawable = ContextCompat.getDrawable(context, R.drawable.ic_add_white_18dp);
      }
    } finally {
      array.recycle();
    }

    skittleContainer = (SkittleContainer) LayoutInflater.from(context)
        .inflate(R.layout.skittle_container, this, false);
    SkittleAdapter skittleAdapter = new SkittleAdapter(color, drawable);
    skittleContainer.setLayoutManager(
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
    skittleContainer.setAdapter(skittleAdapter);
    skittleContainer.setItemAnimator(new ItemAnimator());
    //skittleContainer.addItemDecoration(new SkittleDecorator());
    addView(skittleContainer);
  }

  @Override public void addView(View child) {
    super.addView(child);
    if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout)) addSkittleOnTop();
  }

  @Override public void addView(View child, int index) {
    super.addView(child, index);
    if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout)) addSkittleOnTop();
  }

  @Override public void addView(View child, int width, int height) {
    super.addView(child, width, height);
    if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout)) addSkittleOnTop();
  }

  @Override public void addView(View child, ViewGroup.LayoutParams params) {
    super.addView(child, params);
    if (getChildCount() != 1 && !(child instanceof Snackbar.SnackbarLayout)) addSkittleOnTop();
  }

  /**
   * Utility method called after addView to ensure the skittle container is on top
   */
  private void addSkittleOnTop() {
    detachViewFromParent(skittleContainer);
    attachViewToParent(skittleContainer, getChildCount(), skittleContainer.getLayoutParams());
  }

  public SkittleAdapter getSkittleAdapter() {
    return (SkittleAdapter) skittleContainer.getAdapter();
  }

  int fetchAccentColor(Context mContext) {
    TypedValue typedValue = new TypedValue();

    TypedArray a =
        mContext.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
    int color = a.getColor(0, 0);

    a.recycle();

    return color;
  }
}
