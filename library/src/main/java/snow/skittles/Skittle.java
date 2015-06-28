package snow.skittles;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 *Simple extension of FloatingActionButton with some convenience methods
 */
public class Skittle extends FloatingActionButton {
    private int position;

    public Skittle(Context context) {
        super(context);
    }

    public Skittle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Skittle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }


}
