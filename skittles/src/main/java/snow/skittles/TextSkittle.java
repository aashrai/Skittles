package snow.skittles;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 *
 */
public class TextSkittle extends FrameLayout {

    private int position;
    TextView textView;
    FloatingActionButton miniSkittle;

    public TextSkittle(Context context) {
        super(context);
    }

    public TextSkittle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTag(getResources().getString(R.string.text_skittle_tag));
        textView = (TextView) LayoutInflater.from(getContext())
                .inflate(R.layout.text_skittle_text_view, this, false);
        addView(textView);
        miniSkittle = (FloatingActionButton) LayoutInflater.from(getContext())
                .inflate(R.layout.action_skittle, this, false);
        addView(miniSkittle);
    }

    public TextSkittle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    Skittle getSkittle() {
        return (Skittle) getChildAt(1);
    }

    TextView getTextView() {
        return textView;
    }

    public String getText() {
        return textView.getText().toString();
    }
}
