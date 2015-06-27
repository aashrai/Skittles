package snow.skittles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by aashrai on 27/6/15.
 */
public class TextSkittle {

    private Context context;
    private TextSkittleContainer layout;
    private Skittle skittle;
    private TextView textView;


    public TextSkittle(Context context, ViewGroup parent) {
        this.context = context;
        layout = (TextSkittleContainer) LayoutInflater.from(context)
                .inflate(R.layout.text_action_skittle, parent, false);
        skittle = (Skittle) layout.getChildAt(1);
        textView = (TextView) layout.getChildAt(0);
    }

    public TextSkittle(TextSkittleContainer textSkittleLayout) {

        layout = textSkittleLayout;
        textView = (TextView) textSkittleLayout.getChildAt(0);
        skittle = (Skittle) textSkittleLayout.getChildAt(1);
    }

    public TextSkittleContainer getTextSkittle() {
        return layout;
    }

    public Skittle getSkittle() {
        return skittle;
    }

    public void setIcon(Drawable icon) {
        skittle.setImageDrawable(icon);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setTextColor(@ColorRes int color) {
        textView.setTextColor(context.getResources().getColor(color));
    }

    public void setTextBackgroundColor(@ColorRes int color) {
        textView.setBackgroundColor(context.getResources().getColor(color));
    }

    public void setTextBackground(Drawable background) {
        textView.setBackgroundDrawable(background);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public int getPosition() {
        return layout.getPosition();
    }

    public void setPosition(int position) {
        layout.setPosition(position);
    }
}
