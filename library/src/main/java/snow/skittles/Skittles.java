package snow.skittles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by aashrai on 26/6/15.
 */
@SuppressWarnings("ALL")
public class Skittles {

    SkittleLayout mSkittleLayout;
    Context context;

    public interface SkittleClickListener {
        void onSkittleClick(int id);
    }

    public Skittles(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout) {
        this.mSkittleLayout = mSkittleLayout;
        this.context = context;
    }

    public void addSkittle(@NonNull Skittle skittle) {

        FloatingActionButton fab = (FloatingActionButton) LayoutInflater.from(context)
                .inflate(R.layout.action_skittle, mSkittleLayout.getSkittleContainer(), false);

        if (skittle.hasDrawable())
            fab.setImageDrawable(skittle.getIcon());
        else
            fab.setImageBitmap(skittle.getBitmapIcon());
        fab.setId(skittle.getId());

        fab.setAlpha(0f);
        mSkittleLayout.addFab(fab);
    }


    public void addTextSkittle(@NonNull TextSkittle textSkittle) {

        FrameLayout textSkittleContainer = (FrameLayout) LayoutInflater.from(context)
                .inflate(R.layout.text_action_skittle, mSkittleLayout.getSkittleContainer(), false);

        TextView textView = (TextView) textSkittleContainer.getChildAt(0);
        textView.setText(textSkittle.text);
        textView.setTextColor(context.getResources().getColor(textSkittle.getTextColor()));
        textView.setBackgroundColor(context.getResources()
                .getColor(textSkittle.getTextBackgroundColor()));
        textView.setBackgroundDrawable(textSkittle.getTextBackground());

        FloatingActionButton fab = (FloatingActionButton) textSkittleContainer.getChildAt(1);

        if (textSkittle.hasDrawable())
            fab.setImageDrawable(textSkittle.getIcon());
        else
            fab.setImageBitmap(textSkittle.getBitmapIcon());

        fab.setId(textSkittle.getId());

        textSkittleContainer.setAlpha(0f);
        mSkittleLayout.addFab(textSkittleContainer);

    }


    static public class Skittle {
        private int id;
        private Drawable icon;
        private Bitmap bitmapIcon;


        public Skittle(int id, Drawable icon) {
            this.id = id;
            this.icon = icon;
        }

        public Skittle(int id, Bitmap bitmapIcon) {
            this.id = id;
            this.bitmapIcon = bitmapIcon;
        }

        public int getId() {
            return id;
        }

        public Drawable getIcon() {
            return icon;
        }

        public Bitmap getBitmapIcon() {
            return bitmapIcon;
        }

        public boolean hasDrawable() {
            return !(icon == null);
        }

    }

    static public class TextSkittle extends Skittle {

        String text;
        @ColorRes
        int textColor = android.R.color.black;
        Drawable textBackground;
        @ColorRes
        int textBackgroundColor = android.R.color.transparent;

        public TextSkittle(int id, Drawable icon, String text) {
            super(id, icon);
            this.text = text;
        }

        public TextSkittle(int id, Bitmap bitmapIcon, String text) {
            super(id, bitmapIcon);
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setTextColor(@ColorRes int textColor) {
            this.textColor = textColor;
        }

        public void setTextBackground(Drawable textBackground) {
            this.textBackground = textBackground;
        }

        public void setTextBackgroundColor(@ColorRes int textBackgroundColor) {
            this.textBackgroundColor = textBackgroundColor;
        }

        public int getTextColor() {
            return textColor;
        }

        public Drawable getTextBackground() {
            return textBackground;
        }

        public int getTextBackgroundColor() {
            return textBackgroundColor;
        }
    }
}
