package snow.skittles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by aashrai on 26/6/15.
 */
@SuppressWarnings("ALL")
public class SkittleBuilder {

    SkittleLayout mSkittleLayout;
    TextSkittle textSkittle;
    Context context;
    int skittleCount = 1;

    private SkittleClickListener mListener;

    public interface SkittleClickListener {
        void onSkittleClick(Skittle skittle);

        void onTextSkittleClick(TextSkittle textSkittle);
    }

    public SkittleBuilder(@NonNull Context context, @NonNull SkittleLayout mSkittleLayout) {
        this.mSkittleLayout = mSkittleLayout;
        this.context = context;
    }

    public void addSkittle(@Nullable Drawable icon) {

        Skittle skittle = (snow.skittles.Skittle) LayoutInflater.from(context)
                .inflate(R.layout.action_skittle, mSkittleLayout.getSkittleContainer(), false);
        skittle.setImageDrawable(icon);
        skittle.setAlpha(0f);

        skittle.setOnClickListener(mSkittleClickListener);
        skittle.setPosition(skittleCount++);
        mSkittleLayout.addFab(skittle);

    }


    public void addTextSkittle(@NonNull TextSkittle textSkittle) {

        textSkittle.getTextSkittle().setAlpha(0f);
        textSkittle.setPosition(skittleCount++);
        mSkittleLayout.addFab(textSkittle.getTextSkittle());
        textSkittle.getSkittle().setOnClickListener(mTextSkittleClickListener);

    }

    public TextSkittle makeTextSkittle(@Nullable Drawable drawable, @NonNull String text) {

        textSkittle = new TextSkittle(context, mSkittleLayout.getSkittleContainer());
        textSkittle.setIcon(drawable);
        textSkittle.setText(text);

        return textSkittle;
    }

    View.OnClickListener mSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Skittle Click");
            getmListener().onSkittleClick((Skittle) v);
        }
    };

    View.OnClickListener mTextSkittleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Skittles", "Text Skittle Click");
            getmListener().onTextSkittleClick(new TextSkittle((TextSkittleContainer) v.getParent()));
        }
    };

    public SkittleClickListener getmListener() {
        return mListener;
    }

    public void setSkittleListener(@NonNull SkittleClickListener mListener) {
        this.mListener = mListener;
    }
}
