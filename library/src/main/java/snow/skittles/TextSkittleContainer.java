package snow.skittles;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by aashrai on 27/6/15.
 */
public class TextSkittleContainer extends FrameLayout {

    int position;

    public TextSkittleContainer(Context context) {
        super(context);
    }

    public TextSkittleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextSkittleContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
