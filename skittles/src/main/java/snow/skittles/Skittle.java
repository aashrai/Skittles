package snow.skittles;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import snow.skittles.vectordrawable.VectorDrawableCompat;

/**
 * Simple extension of FloatingActionButton with some convenience methods
 */
public class Skittle extends FloatingActionButton {
    private int position;
    private boolean clickable;
    private ColorStateList mBackgroundColorStateList;

    public Skittle(Context context) {
        super(context);
    }

    public Skittle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Skittle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageDrawable(VectorDrawableCompat.createFromResource(getResources(),R.drawable.plus_white));
    }

    void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    void setClickability(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setBackgroundTintList(ColorStateList tint) {
        super.setBackgroundTintList(tint);
        mBackgroundColorStateList = tint;
    }

    @Nullable
    @Override
    public ColorStateList getBackgroundTintList() {
        return mBackgroundColorStateList;
    }
}
